package me.tehbeard.vocalise.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

/**
 * Concrete version of message prompt
 * @author James
 *
 */
public class MsgPrompt extends MessagePrompt{

    String msg;
    Prompt prompt;
    public MsgPrompt(String message,Prompt prompt){
        msg = message;
        this.prompt = prompt;
    }
    public String getPromptText(ConversationContext context) {
        return msg;
    }

    @Override
    protected Prompt getNextPrompt(ConversationContext context) {
        return prompt;
    }

}
