package me.tehbeard.vocalise.prompts;

import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * gooey wrapper for simple boolean inputs
 * @author James
 *
 */
public class BoolPrompt extends BooleanPrompt {

	protected String txt;
	protected Prompt t;
	protected Prompt f;
	
	
	
	
	/**
	 * @param text text prompt to display
	 * @param t prompt to goto if true
	 * @param f prompt to goto if false
	 */
	protected BoolPrompt(String text, Prompt t, Prompt f) {
		super();
		this.txt = text;
		this.t = t;
		this.f = f;
	}

	public String getPromptText(ConversationContext context) {
		// TODO Auto-generated method stub
		return txt;
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context,
			boolean input) {
		return input ? t : f;
	}

}
