package me.tehbeard.vocalise.prompts.input;

import java.util.regex.Pattern;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

@PromptTag(tag="inpregex")
public class InputRegexPrompt extends ValidatingPrompt implements ConfigurablePrompt {

    
    private String input;
    private String session;
    
    private Prompt prompt;
    private Pattern pattern;
    
    public InputRegexPrompt(){
        this("","","");
    }
    
    public InputRegexPrompt(String input,String session,String regex) {
        pattern = Pattern.compile(regex);
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
            String input) {
        context.setSessionData(session, input);
        return prompt;
    }

    public void configure(ConfigurationSection config, PromptBuilder builder) {
        
        pattern = Pattern.compile(config.getString("regex"));
        session = config.getString("variable");
        input = config.getString("text");
        builder.makePromptRef(config.getString("id"),this);
        setPrompt(config.isString("next") ? builder.locatePromptById(config.getString("next")) : builder.generatePrompt(config.getConfigurationSection("next")));
        
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        return pattern.matcher(input).matches();
    }

}
