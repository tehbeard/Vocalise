package me.tehbeard.vocalise.parser;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.Prompt;

/**
 * Configurable prompts have a method through which they can be configured.
 * @author James
 *
 */
public interface ConfigurablePrompt extends Prompt{

    
    /**
     * Configure this prompt for use
     * @param section configuration section to work from
     * @param builder
     */
    public void configure(ConfigurationSection config,PromptBuilder builder);
    
}
