package me.gizmonster.anticacore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LocalizedName implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(player.hasPermission("triadutils.ln"))) {
            player.sendMessage(ChatColor.RED + "You have to have that permission, buddy.");
            return true;
        }
        if (args == null) {
            player.sendMessage(ChatColor.RED + "What're you doing pal, you need to have a name ready.");
            return true;
        }
        if (item == null) {
            player.sendMessage(ChatColor.RED + "You need an item in your hand for that.");
            return true;
        }
        StringBuilder sb = new StringBuilder(args[0]);
        for (int i = 2; i < args.length; i++) {
            sb.append(' ').append(args[i]);
        }
        String localizedname = sb.toString();
        ItemMeta meta = item.getItemMeta();
        meta.setLocalizedName(localizedname);
        item.setItemMeta(meta);
        player.sendMessage(ChatColor.GREEN + "Localized name set to " + ChatColor.GRAY + localizedname);
        return true;
    }
}
