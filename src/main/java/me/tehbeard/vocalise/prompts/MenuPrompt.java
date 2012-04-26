package me.tehbeard.vocalise.prompts;

import java.util.HashMap;
import java.util.Map;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;
import me.tehbeard.vocalise.parser.VocaliseParserException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
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
public class MenuPrompt extends ValidatingPrompt implements ConfigurablePrompt{

	protected String text;
	Map<String,Prompt> prompts;

	/**
	 * Provide flavour text for the menu
	 * @param text
	 */
	public MenuPrompt(){
		this("Select an option");
	}
	public MenuPrompt(String text) {
		this.text = text;
		prompts = new HashMap<String, Prompt>();
	}
	/**
	 * Add a menu option
	 * @param name name of option
	 * @param prompt prompt
	 */
	public void addMenuOption(String name,Prompt prompt){
		prompts.put(name, prompt);
	}

	public String getPromptText(ConversationContext context) {
		String msg = "";
		int i =0;
		for(String opt : prompts.keySet()){
			if(msg.length() !=0){msg += ",";}
			msg += opt;
			i++;
			if(i==5){context.getForWhom().sendRawMessage(msg);msg="";i=0;}
		}
		if(msg.length() > 0){
		    context.getForWhom().sendRawMessage(msg);
		}
		return text;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return prompts.containsKey(input);
	}
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context,
			String input) {
		return prompts.get(input);
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



}
