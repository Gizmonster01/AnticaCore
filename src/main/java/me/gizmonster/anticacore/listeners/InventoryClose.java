package me.gizmonster.anticacore.listeners;

import me.gizmonster.anticacore.AnticaCorePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.awt.*;

public class InventoryClose implements Listener {

    private AnticaCorePlugin plugin;

    public InventoryClose(AnticaCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (plugin.shopManager.shopTracker.containsKey(player.getUniqueId())) {
            plugin.shopManager.sessionCloseMessage(player);
        }
    }
}
