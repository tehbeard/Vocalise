package me.tehbeard.vocalise.prompts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;
import me.tehbeard.vocalise.parser.VocaliseParserException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

/**
 * Implements a text menu prompt for bukkit's conversation API
 * Menu options are added by the addMenuOption(String, Prompt) method
 * These menu's are static, and cannot change
 * @author James
 *
 */
@PromptTag(tag="menu")
public class MenuPrompt extends NumericPrompt implements ConfigurablePrompt{

    protected String text;
    List<MenuEntry> prompts;
    //Map<String,Prompt> prompts;

    /**
     * Provide flavour text for the menu
     * @param text
     */
    public MenuPrompt(){
        this("Select an option");
    }
    public MenuPrompt(String text) {
        this.text = text;
        prompts = new ArrayList<MenuEntry>();
    }
    /**
     * Add a menu option
     * @param name name of option
     * @param prompt prompt
     */
    public void addMenuOption(String name,Prompt prompt){
        prompts.add(new MenuEntry(name,prompt));
    }

    public String getPromptText(ConversationContext context) {

        int i = 0;
        for(MenuEntry opt : prompts){
            context.getForWhom().sendRawMessage("[" + i++ +"] " + opt.text);
        }

        return text;
    }

    @Override
    protected boolean isNumberValid(ConversationContext context, Number input) {
        int i = input.intValue();
        return (i>=0) && (i<prompts.size());
    }
    
    @Override
    protected Prompt acceptValidatedInput(ConversationContext arg0, Number number) {
        return prompts.get(number.intValue()).prompt;
    }


    public void configure(ConfigurationSection config, PromptBuilder builder) {
        builder.makePromptRef(config.getString("id"),this);
        if(!config.contains("options")){
            throw new VocaliseParserException("Menu prompts need an options key for menu items");
        }
        for(String s : config.getConfigurationSection("options").getKeys(false)){
            System.out.println("Parsing option " + s);
            addMenuOption(
                    config.getConfigurationSection("options." + s).getString("name"),

                    config.getConfigurationSection("options." + s).isString("prompt") ? 
                            builder.locatePromptById(config.getString("options." + s + ".prompt")) : 
                                builder.generatePrompt(config.getConfigurationSection("options." + s + ".prompt"))

                    );
        }

    }


    private class MenuEntry{
        public String text;
        public Prompt prompt;
        /**
         * @param text
         * @param prompt
         */
        public MenuEntry(String text, Prompt prompt) {
            this.text = text;
            this.prompt = prompt;
        }


    }



}
