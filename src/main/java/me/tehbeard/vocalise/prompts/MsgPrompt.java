package me.tehbeard.vocalise.prompts;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

/**
 * Concrete version of message prompt
 * @author James
 *
 */
@PromptTag(tag="msg")
public class MsgPrompt extends MessagePrompt implements ConfigurablePrompt{

    String msg;
    Prompt prompt;
    
    public MsgPrompt(){
        msg = "";
        prompt = null;
    }
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
    public void configure(ConfigurationSection section, PromptBuilder builder) {
        
        msg = section.getString("text");
        builder.makePromptRef(section.getString("id"),this);
        prompt = section.isString("next") ? builder.locatePromptById(section.getString("next")) : builder.generatePrompt(section.getConfigurationSection("next"));
    }
}
