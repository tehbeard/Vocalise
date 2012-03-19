package me.tehbeard.vocalise.prompts;

import java.util.List;

import org.bukkit.conversations.Prompt;

public interface MenuPromptFeeder {

	public  List<String> getMenuOptions();
	
	public Prompt getPrompt(String opt);
}
