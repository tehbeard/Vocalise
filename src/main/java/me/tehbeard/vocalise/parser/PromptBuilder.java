package me.tehbeard.vocalise.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import me.tehbeard.vocalise.prompts.MenuPrompt;
import me.tehbeard.vocalise.prompts.MsgPrompt;
import me.tehbeard.vocalise.prompts.QuickBooleanPrompt;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.conversations.Prompt;

/**
 * Construct a prompt graph from a supplied file/inputstream
 * @author James
 *
 */
public class PromptBuilder {

    private Map<String,Prompt> promptDatabase = new HashMap<String, Prompt>();
    private Prompt startPrompt;
    
    
    public PromptBuilder(File file){
        YamlConfiguration c = new YamlConfiguration();
        try {
            c.load(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        startPrompt = generatePrompt(c);
    }

    public PromptBuilder(InputStream is){
        YamlConfiguration c = new YamlConfiguration();
        try {
            c.load(is);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        startPrompt = generatePrompt(c);
    }

    
    private Prompt generatePrompt(ConfigurationSection config){
        promptDatabase.put("NULL", null);
        Prompt prompt = null;

        String id = config.getString("id","");
        String type = config.getString("type");
        String message = config.getString("text");

        //check for pointers

        if(type.equalsIgnoreCase("msg")){
            prompt = new MsgPrompt(message, config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
        }

        if(type.equalsIgnoreCase("bool")){
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

    public Prompt getPromptById(String id){
        try{
            return locatePromptById(id);
        }
        catch(VocaliseParserException e){
            return null;
        }
    }

    public Prompt getStartPrompt(){
        return startPrompt;
    }
}
