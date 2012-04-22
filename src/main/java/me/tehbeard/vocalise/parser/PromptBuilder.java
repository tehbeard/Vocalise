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
import me.tehbeard.vocalise.prompts.input.InputBooleanPrompt;
import me.tehbeard.vocalise.prompts.input.InputNumberPrompt;
import me.tehbeard.vocalise.prompts.input.InputPlayerNamePrompt;
import me.tehbeard.vocalise.prompts.input.InputRegexPrompt;
import me.tehbeard.vocalise.prompts.input.InputStringPrompt;

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

    public PromptBuilder(){
        
    }
    
    public PromptBuilder (File file,Map<String,Prompt> prompts){
        promptDatabase.putAll(prompts);
        load(file);
    }
    
    public PromptBuilder (InputStream is,Map<String,Prompt> prompts){
        promptDatabase.putAll(prompts);
        load(is);
    }
    
    public PromptBuilder (File file){
        load(file);
    }
    
    public PromptBuilder (InputStream is){
        load(is);
    }
    
    public void load(File file){
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

    public void load(InputStream is){
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
        System.out.println(" prompt of type " + type + ", with msg " + message);
        //check for pointers

        if(type.equalsIgnoreCase("msg")){
            MsgPrompt mp = new MsgPrompt(message,null);
            makePromptRef(id,mp);
            mp.setNextPrompt(config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
            prompt = mp;

        }

        if(type.equalsIgnoreCase("bool")){
            if(!config.contains("t") || !config.contains("f") ){
                throw new VocaliseParserException("Boolean prompt must have a true and a false declared");
            }
            QuickBooleanPrompt qbp = new QuickBooleanPrompt(message,null,null);
            makePromptRef(id,qbp);
            qbp.setPrompts(config.isString("t") ? locatePromptById(config.getString("t")) : generatePrompt(config.getConfigurationSection("t")), config.isString("f") ? locatePromptById(config.getString("f")) : generatePrompt(config.getConfigurationSection("f")));
            prompt = qbp;
        }

        if(type.equalsIgnoreCase("menu")){
            MenuPrompt mp = new MenuPrompt();
            makePromptRef(id,mp);
            if(!config.contains("options")){
                throw new VocaliseParserException("Menu prompts need an options key for menu items");
            }
            for(String s : config.getConfigurationSection("options").getKeys(false)){
                System.out.println("Parsing option " + s);
                mp.addMenuOption(
                        config.getConfigurationSection("options." + s).getString("name"),

                        config.getConfigurationSection("options." + s).isString("prompt") ? 
                                locatePromptById(config.getString("options." + s + ".prompt")) : 
                                    generatePrompt(config.getConfigurationSection("options." + s + ".prompt"))

                        );
            }
            prompt = mp;
        }

        ////////////////////////
        // input prompts      //
        ////////////////////////
        
        if(type.equalsIgnoreCase("inpstr")){
            InputStringPrompt isp = new InputStringPrompt(message,config.getString("variable"));
            makePromptRef(id,isp);
            isp.setPrompt(config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
            prompt = isp;

        }
        
        if(type.equalsIgnoreCase("inpnum")){
            InputNumberPrompt isp = new InputNumberPrompt(message,config.getString("variable"));
            makePromptRef(id,isp);
            isp.setPrompt(config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
            prompt = isp;

        }
        
        if(type.equalsIgnoreCase("inpbool")){
            InputBooleanPrompt isp = new InputBooleanPrompt(message,config.getString("variable"));
            makePromptRef(id,isp);
            isp.setPrompt(config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
            prompt = isp;

        }
        
        if(type.equalsIgnoreCase("inpply")){
            InputPlayerNamePrompt isp = new InputPlayerNamePrompt(message,config.getString("variable"));
            makePromptRef(id,isp);
            isp.setPrompt(config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
            prompt = isp;

        }
        
        if(type.equalsIgnoreCase("inpregex")){
            InputRegexPrompt isp = new InputRegexPrompt(message,config.getString("variable"),config.getString("regex"));
            makePromptRef(id,isp);
            isp.setPrompt(config.isString("next") ? locatePromptById(config.getString("next")) : generatePrompt(config.getConfigurationSection("next")));
            prompt = isp;

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
    
    public void makePromptRef(String id,Prompt prompt){
        if(!id.equalsIgnoreCase("") && prompt != null){
            if(!promptDatabase.containsKey(id)){
                promptDatabase.put(id,prompt);
            }
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
