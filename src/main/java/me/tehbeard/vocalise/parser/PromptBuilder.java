package me.tehbeard.vocalise.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import me.tehbeard.utils.factory.ConfigurableFactory;
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
    
    private final ConfigurableFactory<ConfigurablePrompt, PromptTag> promptFactory;

    public PromptBuilder(){
        promptFactory = new ConfigurableFactory<ConfigurablePrompt, PromptTag>(PromptTag.class) {
            
            @Override
            public String getTag(PromptTag annotation) {
                return annotation.tag();
            }
        };
        promptFactory.addProduct(MsgPrompt.class);
        promptFactory.addProduct(QuickBooleanPrompt.class);
        promptFactory.addProduct(MenuPrompt.class);
        
        
    }
    
    public PromptBuilder (File file,Map<String,Prompt> prompts){
        this();
        promptDatabase.putAll(prompts);
        load(file);
    }
    
    public PromptBuilder (InputStream is,Map<String,Prompt> prompts){
        this();
        promptDatabase.putAll(prompts);
        load(is);
    }
    
    public PromptBuilder (File file){
        this();
        load(file);
    }
    
    public PromptBuilder (InputStream is){
        this();
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

    
    public Prompt generatePrompt(ConfigurationSection config){
        promptDatabase.put("NULL", null);
        Prompt prompt = null;

        String id = config.getString("id","");
        String type = config.getString("type");
        String message = config.getString("text");
        System.out.println(" prompt of type " + type + ", with msg " + message);
        //check for pointers

        
        ConfigurablePrompt p = promptFactory.getProduct(type);
        if(p!=null){
            System.out.println("Prompt is being configured");
            p.configure(config,this);
            prompt = p;
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

    public Prompt locatePromptById(String id){
        if(promptDatabase.containsKey(id)){
            return promptDatabase.get(id);
        }
        else
        {
            throw new VocaliseParserException("Could not find node referenced by ID [" + id + "]");
        }

    }
    
    public void makePromptRef(String id,Prompt prompt){
        if(id != null && !id.equalsIgnoreCase("") && prompt != null){
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
