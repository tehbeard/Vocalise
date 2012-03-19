package me.tehbeard.vocalise.prompts;

import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * Wraps the abstract Boolean Prompt to provide a simple
 * version for cases where the true/false prompts are static 
 * @author James
 *
 */
public class BetterBooleanPrompt extends BooleanPrompt {

	protected String txt;
	protected Prompt t;
	protected Prompt f;
	
	
	
	
	/**
	 * @param text text prompt to display
	 * @param t prompt to goto if true
	 * @param f prompt to goto if false
	 */
	protected BetterBooleanPrompt(String text, Prompt t, Prompt f) {
		super();
		this.txt = text;
		this.t = t;
		this.f = f;
	}

	public final String getPromptText(ConversationContext context) {
		// TODO Auto-generated method stub
		return txt;
	}

	@Override
	protected final Prompt acceptValidatedInput(ConversationContext context,
			boolean input) {
		return input ? t : f;
	}

}
