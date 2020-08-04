package me.gizmonster.anticacore.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MessagingUtils {
    public void sendError(Player player, boolean playSound, String string) {
        player.sendMessage(ChatColor.of("#ff2e46") + "✘ " + string);
        if (playSound) {
            player.playSound(player.getLocation(), "error", 1, 1);
        }
    }
    public void sendSuccessPurchase(Player player, boolean playSound, String string) {
        player.sendMessage(ChatColor.of("#00ff44") + "✔ " + string);
        if (playSound) {
            player.playSound(player.getLocation(), "chaching", 1, 1);
        }
    }
}
