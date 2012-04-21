package me.tehbeard.vocalise.prompts.input;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.PlayerNamePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class InputPlayerNamePrompt extends PlayerNamePrompt {

    private String input;
    private String session;
    
    private Prompt prompt;
    
    public InputPlayerNamePrompt(String input,String session){
        super(new FakePlugin());
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
            Player input) {
        context.setSessionData(session, input);
        return prompt;
    }

}
