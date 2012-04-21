package me.tehbeard.vocalise.parser;

import java.util.HashMap;
import java.util.Map;

import me.tehbeard.vocalise.prompts.MenuPrompt;
import me.tehbeard.vocalise.prompts.MsgPrompt;
import me.tehbeard.vocalise.prompts.QuickBooleanPrompt;

import org.bukkit.configuration.ConfigurationSection;

import org.bukkit.conversations.Prompt;

public class PromptBuilder {

    private Map<String,Prompt> promptDatabase = new HashMap<String, Prompt>();

    private Prompt generatePrompt(ConfigurationSection config){
        Prompt prompt = null;

        String id = config.getString("id","");
        String type = config.getString("type");
        String message = config.getString("text");

        //check for pointers





        if(type.equalsIgnoreCase("msg")){
            prompt = new MsgPrompt(message, config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
        }

        if(type.equalsIgnoreCase("boolean")){
            if(!config.contains("true") || !config.contains("false") ){
                throw new VocaliseParserException("Boolean prompt must have a true and a false declared");
            }
            prompt = new QuickBooleanPrompt(message, config.isString("true") ? locatePromptById(config.getString("true")) : generatePrompt(config.getConfigurationSection("true")), config.isString("false") ? locatePromptById(config.getString("false")) : generatePrompt(config.getConfigurationSection("false")));
        }

        if(type.equalsIgnoreCase("menu")){
            MenuPrompt mp = new MenuPrompt();
            if(!config.contains("options")){
                throw new VocaliseParserException("Menu prompts need an options key for menu items");
            }
            for(String s : config.getConfigurationSection("options").getKeys(false)){
                mp.addMenuOption(
                        config.getConfigurationSection("options." + s).getString("name"),
                        
                        config.getConfigurationSection("options." + s).isString("prompt") ? 
                                locatePromptById(config.getString("options." + s + ".prompt")) : 
                                    generatePrompt(config.getConfigurationSection("options." + s + ".prompt"))
                        
                        );
            }
        }

        if(!id.equalsIgnoreCase("") && prompt != null){
            if(!promptDatabase.containsKey(id)){
                promptDatabase.put(id,prompt);
            }
        }
        return prompt;


    }

    private Prompt locatePromptById(String id){
        if(promptDatabase.containsKey(id)){
            return promptDatabase.get(id);
        }
        else
        {
            throw new VocaliseParserException("Could not find node referenced by ID [" + id + "]");
        }

    }
}
