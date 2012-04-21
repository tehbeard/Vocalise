package me.tehbeard.vocalise.prompts.input;

import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

public class InputBooleanPrompt extends BooleanPrompt {

    private String input;
    private String session;
    
    private Prompt prompt;
    
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

}
