package me.tehbeard.vocalise.prompts;

import java.util.ArrayList;
import java.util.List;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;
import me.tehbeard.vocalise.parser.VocaliseParserException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

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
    List<PromptOpt> prompts;

    /**
     * Provide flavour text for the menu
     * @param text
     */
    public MenuPrompt(){
        this("Select an option");
    }
    public MenuPrompt(String text) {
        this.text = text;
        prompts = new ArrayList<PromptOpt>();
    }
    /**
     * Add a menu option
     * @param name name of option
     * @param prompt prompt
     */
    public void addMenuOption(String name,Prompt prompt){
        prompts.add(new PromptOpt(name, prompt));
    }

    public String getPromptText(ConversationContext context) {

        int i = 0;
        for(PromptOpt opt : prompts){
            context.getForWhom().sendRawMessage(i + ") " + opt.name);
            i++;
        }
        return text;
    }


    public void configure(ConfigurationSection config, PromptBuilder builder) {
        builder.makePromptRef(config.getString("id"),this);
        if(!config.contains("options")){
            throw new VocaliseParserException("Menu prompts need an options key for menu items");
        }
        for(String s : config.getConfigurationSection("options").getKeys(false)){
            addMenuOption(
                    config.getConfigurationSection("options." + s).getString("name"),

                    config.getConfigurationSection("options." + s).isString("prompt") ? 
                            builder.locatePromptById(config.getString("options." + s + ".prompt")) : 
                                builder.generatePrompt(config.getConfigurationSection("options." + s + ".prompt"))

                    );
        }

    }


    private class PromptOpt{
        String name;
        Prompt prompt;
        public PromptOpt(String name, Prompt prompt){
            this.name = name;
            this.prompt = prompt;
        }
    }




    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number number) {
        return (number.intValue() >=0 && number.intValue() < prompts.size()) ? prompts.get(number.intValue()).prompt : this;
    }
}