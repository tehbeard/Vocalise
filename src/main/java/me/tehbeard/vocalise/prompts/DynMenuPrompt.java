package me.tehbeard.vocalise.prompts;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.conversations.ValidatingPrompt;

/**
 * Implements a text menu prompt for bukkit's conversation API
 * This differs from the menu prompt as it gets the menu at the time it is called
 * 
 * @author James
 *
 */
public class DynMenuPrompt extends ValidatingPrompt{

	protected String text;
	protected MenuPromptFeeder feeder;
	/**
	 * Provide flavour text for the menu
	 * @param text
	 */
	public DynMenuPrompt(MenuPromptFeeder feeder){
		this(feeder,"Select an option");
	}
	public DynMenuPrompt(MenuPromptFeeder feeder,String text) {
		this.text = text;
		this.feeder=  feeder;
	}

	
	public String getPromptText(ConversationContext context) {
		String msg = "";
		int i =0;
		for(String opt : feeder.getMenuOptions()){
			if(msg.length() !=0){msg += ",";}
			msg += opt;
			i++;
			if(i==5){context.getForWhom().sendRawMessage(msg);msg="";i=0;}
		}
		return text;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		for(String opt : feeder.getMenuOptions()){
			if(opt.equalsIgnoreCase(input)){
				return true;
			}
		}
		return false;
	}
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context,
			String input) {
		return feeder.getPrompt(input);
	}



}
