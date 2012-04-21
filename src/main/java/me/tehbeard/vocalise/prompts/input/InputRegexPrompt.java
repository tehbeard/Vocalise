package me.tehbeard.vocalise.prompts.input;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.RegexPrompt;

public class InputRegexPrompt extends RegexPrompt {

    
    private String input;
    private String session;
    
    private Prompt prompt;
    
    public InputRegexPrompt(String input,String session,String regex) {
        super(regex);
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

}
