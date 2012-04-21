package me.tehbeard.vocalise.prompts;

import org.bukkit.ChatColor;
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
        return colorise(msg);
    }
    
    public void setNextPrompt(Prompt p){
        prompt = p;
    }

    @Override
    protected Prompt getNextPrompt(ConversationContext context) {
        return prompt;
    }

    private String colorise(String str){
        for(int i =0;i<=9; i++){
            str = str.replace("\\&\\"+i,ChatColor.getByChar("" + i).toString());
        }
        
        for(char i ='a';i<='f'; i++){
            str = str.replace("\\&\\"+i,ChatColor.getByChar(i).toString());
        }
        return str;
    }
}
