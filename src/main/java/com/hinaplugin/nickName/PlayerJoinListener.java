package com.hinaplugin.nickName;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

import java.util.Set;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();

        final ConfigurationSection section = NickName.config.getConfigurationSection("nickname");

        if (section == null){
            return;
        }

        final Set<String> keys = section.getKeys(false);

        if (keys.isEmpty()){
            return;
        }

        if (!keys.contains(player.getName())){
            return;
        }

        final String nickname = NickName.config.getString("nickname." + player.getName(), player.getName());

        final Team team = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
        if (team == null){
            player.displayName(Component.text(nickname));
            player.playerListName(Component.text(nickname));
        }else {
            player.displayName(team.prefix().append(Component.text(nickname)).append(team.suffix()).color(team.color()));
            player.playerListName(team.prefix().append(Component.text(nickname)).append(team.suffix()).color(team.color()));
        }
    }
}
