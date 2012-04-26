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
 * Wraps the abstract Boolean Prompt to provide a simple
 * version for cases where the true/false prompts are static 
 * @author James
 *
 */
@PromptTag(tag="bool")
public class QuickBooleanPrompt extends BooleanPrompt implements ConfigurablePrompt{

	protected String txt;
	protected Prompt t;
	protected Prompt f;
	
	
	
	
	public QuickBooleanPrompt(){
	    this("",null,null);
	}
	/**
	 * @param text text prompt to display
	 * @param t prompt to goto if true
	 * @param f prompt to goto if false
	 */
	public QuickBooleanPrompt(String text, Prompt t, Prompt f) {
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
		return input ? t : f;
	}

    public void configure(ConfigurationSection config, PromptBuilder builder) {
        if(!config.contains("t") || !config.contains("f") ){
            throw new VocaliseParserException("Boolean prompt must have a true and a false declared");
        }
        txt = config.getString("text");
        builder.makePromptRef(config.getString("id"),this);
        setPrompts(config.isString("t") ? builder.locatePromptById(config.getString("t")) : builder.generatePrompt(config.getConfigurationSection("t")), config.isString("f") ? builder.locatePromptById(config.getString("f")) : builder.generatePrompt(config.getConfigurationSection("f")));
        
    }

}
