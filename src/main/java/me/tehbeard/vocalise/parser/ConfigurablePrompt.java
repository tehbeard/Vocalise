package me.tehbeard.vocalise.parser;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurablePrompt {

    
    /**
     * Configure this prompt for use
     * @param section configuration section to work from
     * @param builder
     */
    public void configure(ConfigurationSection section,PromptBuilder builder);
    
}
