package me.tehbeard.vocalise.prompts.input;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import com.avaje.ebean.EbeanServer;

public class FakePlugin implements Plugin {

    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        // TODO Auto-generated method stub
        return false;
    }

    public File getDataFolder() {
        // TODO Auto-generated method stub
        return null;
    }

    public PluginDescriptionFile getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    public FileConfiguration getConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    public InputStream getResource(String filename) {
        // TODO Auto-generated method stub
        return null;
    }

    public void saveConfig() {
        // TODO Auto-generated method stub
        
    }

    public void saveDefaultConfig() {
        // TODO Auto-generated method stub
        
    }

    public void saveResource(String resourcePath, boolean replace) {
        // TODO Auto-generated method stub
        
    }

    public void reloadConfig() {
        // TODO Auto-generated method stub
        
    }

    public PluginLoader getPluginLoader() {
        // TODO Auto-generated method stub
        return null;
    }

    public Server getServer() {
        
        return Bukkit.getServer();
    }

    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    public void onDisable() {
        // TODO Auto-generated method stub
        
    }

    public void onLoad() {
        // TODO Auto-generated method stub
        
    }

    public void onEnable() {
        // TODO Auto-generated method stub
        
    }

    public boolean isNaggable() {
        // TODO Auto-generated method stub
        return false;
    }

    public void setNaggable(boolean canNag) {
        // TODO Auto-generated method stub
        
    }

    public EbeanServer getDatabase() {
        // TODO Auto-generated method stub
        return null;
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public Logger getLogger() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
