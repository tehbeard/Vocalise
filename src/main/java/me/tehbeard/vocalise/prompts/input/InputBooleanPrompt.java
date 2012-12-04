package me.tehbeard.vocalise.prompts.input;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

@PromptTag(tag="inpbool")
/**
 * Input boolean prompt
 * Configuration format:
 * <code>
 * id: unique id for this prompt
 * type: inpbool
 * variable: where to store value in context
 * next: prompt declaration or string containing prompt id
 * </code>
 * @author James
 *
 */
public class InputBooleanPrompt extends BooleanPrompt implements ConfigurablePrompt{

    private String input;
    private String session;
    
    private Prompt prompt;
    
    public InputBooleanPrompt(){
        this("","");
    }
    
    public InputBooleanPrompt(String input,String session){
        this.input = input;
        this.session = session;
    }
    
    public void setPrompt(Prompt prompt){
        this.prompt = prompt;
    }
    
    public String getPromptText(ConversationContext context) {
        return input;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context,
            boolean input) {
        context.setSessionData(session,input);
        return prompt;
    }

    public void configure(ConfigurationSection config, PromptBuilder builder) {
        session = config.getString("variable");
        input = config.getString("text");
        builder.makePromptRef(config.getString("id"),this);
        setPrompt(config.isString("next") ? builder.locatePromptById(config.getString("next")) : builder.generatePrompt(config.getConfigurationSection("next")));
    }

}
