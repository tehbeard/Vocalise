package me.tehbeard.vocalise.prompts;



import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

/**
 * Implements a text menu prompt for bukkit's conversation API
 * This differs from the menu prompt as it gets the menu at the 
 * time it is called. While this can be done by overriding
 * ValidatingPrompt, DynMenuPrompt fills in the boilerplate code 
 * for listing the menu 
 * 
 * @author James
 *
 */
public class DynamicMenuPrompt extends ValidatingPrompt{

	protected String text;
	protected MenuPromptFeeder feeder;


	/**
	 * Creates a new DynMenuPrompt
	 * @param feeder The object that will feed menu options
	 */
	public DynamicMenuPrompt(MenuPromptFeeder feeder){
		this(feeder,"Select an option");
	}
	/**
	 * Creates a new DynMenuPrompt
     * @param feeder The object that will feed menu options
	 * @param text text prompt to use
	 */
	public DynamicMenuPrompt(MenuPromptFeeder feeder,String text) {
		this.text = text;
		this.feeder=  feeder;
	}

	
	public final String getPromptText(ConversationContext context) {
		String msg = "";
		int i =0;
		for(String opt : feeder.getMenuOptions(context)){
			if(msg.length() !=0){msg += ",";}
			msg += opt;
			i++;
			if(i==5){context.getForWhom().sendRawMessage(msg);msg="";i=0;}
		}
		return text;
	}

	@Override
	protected final boolean isInputValid(ConversationContext context, String input) {
		for(String opt : feeder.getMenuOptions(context)){
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
