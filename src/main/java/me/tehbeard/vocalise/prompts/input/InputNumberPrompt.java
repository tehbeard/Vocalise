package me.tehbeard.vocalise.prompts.input;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

@PromptTag(tag="inpnum")
public class InputNumberPrompt extends NumericPrompt implements ConfigurablePrompt {

    private String input;
    private String session;
    
    private Prompt prompt;
    
    public InputNumberPrompt(){
        this("","");
    }
    
    public InputNumberPrompt(String input,String session){
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
            Number input) {
        context.setSessionData(session, input);
        return prompt;
    }

    public void configure(ConfigurationSection config, PromptBuilder builder) {
        input = config.getString("text");
        session = config.getString("variable");
        builder.makePromptRef(config.getString("id"),this);
        setPrompt(config.isString("next") ? builder.locatePromptById(config.getString("next")) : builder.generatePrompt(config.getConfigurationSection("next")));
        
        
    }

}
