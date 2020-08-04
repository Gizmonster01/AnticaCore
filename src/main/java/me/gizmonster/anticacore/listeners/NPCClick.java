package me.gizmonster.anticacore.listeners;

import me.gizmonster.anticacore.AnticaCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static me.gizmonster.anticacore.utils.ACConstants.storeOwnerKey;

public class NPCClick implements Listener {

    private AnticaCorePlugin plugin;

    public NPCClick(AnticaCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobSetup(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!(plugin.shopManager.setupList.contains(player))) {
            return;
        }
        if (event.getRightClicked() == null) {
            return;
        }
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (!(event.getRightClicked() instanceof LivingEntity)) {
            return;
        }
        LivingEntity entity = (LivingEntity) event.getRightClicked();
        if (!(entity.getPersistentDataContainer().has(storeOwnerKey, PersistentDataType.INTEGER))) {
            plugin.msg.sendError(player, true, "That entity is already this type of NPC");
            plugin.shopManager.setupList.remove(player);
        }
        plugin.shopManager.defineNPC(event.getRightClicked());
        plugin.shopManager.setupList.remove(player);
    }

    @EventHandler
    public void onRightClickMob(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(event.getRightClicked().getName());
        if (event.getRightClicked() == null) {
            return;
        }
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        Entity entity = event.getRightClicked();
        if (entity.getPersistentDataContainer().has(storeOwnerKey, PersistentDataType.INTEGER)) {
            plugin.shopManager.openTodayShop(player);
            return;
        }
    }
}
