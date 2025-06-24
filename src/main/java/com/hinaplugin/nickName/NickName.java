package com.hinaplugin.nickName;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class NickName extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        final PluginCommand command = this.getCommand("nick");
        if (command != null){
            command.setExecutor(new Commands());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
