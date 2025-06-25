package com.hinaplugin.nickName;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NickName extends JavaPlugin {
    public static NickName plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.load();
        final PluginCommand command = this.getCommand("nick");
        if (command != null){
            command.setExecutor(new Commands());
        }
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(this);
    }

    private void load(){
        final File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()){
            this.saveDefaultConfig();
        }

        config = this.getConfig();
    }

    public void save(){
        this.saveConfig();
        this.reloadConfig();
        config = this.getConfig();
    }
}
