package me.tehbeard.vocalise.prompts.input;

import me.tehbeard.vocalise.parser.ConfigurablePrompt;
import me.tehbeard.vocalise.parser.PromptBuilder;
import me.tehbeard.vocalise.parser.PromptTag;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.PlayerNamePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

@PromptTag(tag = "inpply")
public class InputPlayerNamePrompt extends PlayerNamePrompt implements ConfigurablePrompt {

    private String input;
    private String session;
    
    private Prompt prompt;
    
    public InputPlayerNamePrompt(){
        this("","");
    }
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

    public void configure(ConfigurationSection config, PromptBuilder builder) {
        session = config.getString("variable");
        input = config.getString("text");
        builder.makePromptRef(config.getString("id"),this);
        setPrompt(config.isString("next") ? builder.locatePromptById(config.getString("next")) : builder.generatePrompt(config.getConfigurationSection("next")));
        
    }

}
