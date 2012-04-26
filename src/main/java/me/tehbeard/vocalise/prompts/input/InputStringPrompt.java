package me.tehbeard.vocalise.prompts.input;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

@PromptTag(tag="inpstr")
public class InputStringPrompt extends StringPrompt implements ConfigurablePrompt{

    private String msg;
    private String session;
    
    private Prompt prompt;
    
    public InputStringPrompt(){
        this("","");
    }
    public InputStringPrompt(String input,String session){
        this.msg = input;
        this.session = session;
    }
    
    public void setPrompt(Prompt prompt){
        this.prompt = prompt;
    }
    
    public String getPromptText(ConversationContext context) {
        return msg;
    }

    public Prompt acceptInput(ConversationContext context, String input) {
        context.setSessionData(session,input);
        return prompt;
    }

    public void configure(ConfigurationSection config, PromptBuilder builder) {
        msg = config.getString("text");
        session = config.getString("variable");
        builder.makePromptRef(config.getString("id"),this);
        setPrompt(config.isString("next") ? builder.locatePromptById(config.getString("next")) : builder.generatePrompt(config.getConfigurationSection("next")));
    }

}
