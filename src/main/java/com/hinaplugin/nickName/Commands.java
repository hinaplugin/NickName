package com.hinaplugin.nickName;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final MiniMessage miniMessage = MiniMessage.miniMessage();
        if (commandSender instanceof Player player){
            if (strings.length == 0){
                player.sendMessage(miniMessage.deserialize("<red>usage: /nick set <nickname>"));
                player.sendMessage(miniMessage.deserialize("<red>usage: /nick setother <player> <nickname>"));
                player.sendMessage(miniMessage.deserialize("<red>usage: /nick unset"));
                player.sendMessage(miniMessage.deserialize("<red>usage: /nick unsetother <player>"));
                return true;
            }

            final String subCommand = strings[0];
            switch (subCommand){
                case "set" -> {
                    if (strings.length != 2){
                        player.sendMessage(miniMessage.deserialize("<red>usage: /nick set <nickname>"));
                        return true;
                    }
                    if (strings[1].isEmpty()){
                        player.sendMessage(miniMessage.deserialize("<red>ニックネームは1文字以上必要です．"));
                        return true;
                    }
                    final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    final Team team = scoreboard.getPlayerTeam(player);
                    if (team == null){
                        player.displayName(Component.text(strings[1]));
                        player.playerListName(Component.text(strings[1]));
                    }else {
                        player.displayName(team.prefix().append(Component.text(strings[1])).append(team.suffix()).color(team.color()));
                        player.playerListName(team.prefix().append(Component.text(strings[1])).append(team.suffix()).color(team.color()));
                    }
                    player.sendMessage(miniMessage.deserialize("<green>ニックネームを[" + strings[1] + "]に設定しました．"));
                }
                case "setother" -> {
                    if (strings.length != 3){
                        player.sendMessage(miniMessage.deserialize("<red>usage: /nick setother <player> <nickname>"));
                        return true;
                    }
                    if (strings[1].isEmpty()){
                        player.sendMessage(miniMessage.deserialize("<red>usage: /nick setother <player> <nickname>"));
                        return true;
                    }
                    final Player target = Bukkit.getPlayer(strings[1]);
                    if (target == null){
                        player.sendMessage(miniMessage.deserialize("<red>" + strings[1] + "が見つかりませんでした．"));
                        return true;
                    }
                    if (strings[2].isEmpty()){
                        player.sendMessage(miniMessage.deserialize("<red>ニックネームは1文字以上必要です．"));
                        return true;
                    }
                    final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    final Team team = scoreboard.getPlayerTeam(target);
                    if (team == null){
                        target.displayName(Component.text(strings[2]));
                        target.playerListName(Component.text(strings[2]));
                    }else {
                        target.displayName(team.prefix().append(Component.text(strings[2])).append(team.suffix()).color(team.color()));
                        target.playerListName(team.prefix().append(Component.text(strings[2])).append(team.suffix()).color(team.color()));
                    }
                    player.sendMessage(miniMessage.deserialize("<green>" + strings[1] + "のニックネームを[" + strings[1] + "]に設定しました．"));
                }
                case "unset" -> {
                    final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    final Team team = scoreboard.getPlayerTeam(player);
                    if (team == null){
                        player.displayName(Component.text(player.getName()));
                        player.playerListName(Component.text(player.getName()));
                    }else {
                        player.displayName(team.prefix().append(Component.text(player.getName())).append(team.suffix()).color(team.color()));
                        player.playerListName(team.prefix().append(Component.text(player.getName())).append(team.suffix()).color(team.color()));
                    }
                    player.sendMessage(miniMessage.deserialize("<green>ニックネームをリセットしました．"));
                }
                case "unsetother" -> {
                    if (strings.length != 2){
                        player.sendMessage(miniMessage.deserialize("<red>usage: /nick unsetother <player>"));
                        return true;
                    }
                    if (strings[1].isEmpty()){
                        player.sendMessage(miniMessage.deserialize("<red>usage: /nick unsetother <player>"));
                        return true;
                    }
                    final Player target = Bukkit.getPlayer(strings[1]);
                    if (target == null){
                        player.sendMessage(miniMessage.deserialize("<red>" + strings[1] + "が見つかりませんでした．"));
                        return true;
                    }
                    final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    final Team team = scoreboard.getPlayerTeam(target);
                    if (team == null){
                        target.displayName(Component.text(target.getName()));
                        target.playerListName(Component.text(target.getName()));
                    }else {
                        target.displayName(team.prefix().append(Component.text(player.getName())).append(team.suffix()).color(team.color()));
                        target.playerListName(team.prefix().append(Component.text(player.getName())).append(team.suffix()).color(team.color()));
                    }
                    player.sendMessage(miniMessage.deserialize("<green>" + strings[1] + "のニックネームをリセットしました．"));
                }
            }
        }else {
            commandSender.sendMessage(miniMessage.deserialize("<red>このコマンドはプレイヤー限定です．"));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final List<String> complete = Lists.newArrayList();
        if (strings.length == 1){
            if (strings[0].isEmpty()){
                complete.add("set");
                complete.add("setother");
                complete.add("unset");
                complete.add("unsetother");
            }else if ("set".startsWith(strings[0])){
                complete.add("set");
                complete.add("setother");
            }else if ("setother".startsWith(strings[0])){
                complete.add("setother");
            }else if ("unset".startsWith(strings[0])){
                complete.add("unset");
                complete.add("unsetother");
            }else if ("unsetother".startsWith(strings[0])){
                complete.add("unsetother");
            }
        }
        return complete;
    }
}
