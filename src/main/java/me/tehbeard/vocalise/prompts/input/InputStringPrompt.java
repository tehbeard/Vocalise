package me.tehbeard.vocalise.prompts.input;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class InputStringPrompt extends StringPrompt {

    private String input;
    private String session;
    
    private Prompt prompt;
    
    public InputStringPrompt(String input,String session){
        this.input = input;
        this.session = session;
    }
    
    public void setPrompt(Prompt prompt){
        this.prompt = prompt;
    }
    
    public String getPromptText(ConversationContext context) {
        return input;
    }

    public Prompt acceptInput(ConversationContext context, String input) {
        context.setSessionData(session,input);
        return prompt;
    }

}
