package me.gizmonster.anticacore.listeners;

import me.gizmonster.anticacore.AnticaCorePlugin;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.UUID;

public class MenuClick implements Listener {

    private AnticaCorePlugin plugin;

    public MenuClick(AnticaCorePlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void clickOnMenuItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.getUniqueID();
        if (plugin.inventoryUtils.isCustomMenu(event.getInventory().getItem(0))) {
            return;
        }
        if (event.getCurrentItem().getType().isAir() || event.getCurrentItem() == null) {
            return;
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getType() == InventoryType.PLAYER) {
            return;
        }
        if (!(plugin.inventoryUtils.isCustomMenu(event.getInventory().getItem(0)))) {
            return;
        }
        event.setCancelled(true);
    }
}
