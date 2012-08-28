package me.tehbeard.vocalise.prompts;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;
import me.tehbeard.vocalise.parser.VocaliseParserException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

/**
 * Confirmation class for use in commands to ensure they want to do something
 * @author James
 *
 */
public abstract class QuickConfirmationPrompt extends BooleanPrompt{

	protected String txt;
	protected Prompt t;
	protected Prompt f;
	
	
	
	
	public QuickConfirmationPrompt(){
	    this("",null,null);
	}
	/**
	 * @param text text prompt to display
	 * @param t prompt to goto if true
	 * @param f prompt to goto if false
	 */
	public QuickConfirmationPrompt(String text, Prompt t, Prompt f) {
		super();
		this.txt = text;
		this.t = t;
		this.f = f;
	}

	public void setPrompts(Prompt t,Prompt f){
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
	    called(input);
		return null;
	}
	
	public abstract void called(boolean result);

}
