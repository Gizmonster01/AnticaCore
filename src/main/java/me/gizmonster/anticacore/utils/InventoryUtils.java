package me.gizmonster.anticacore.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;

public class InventoryUtils {
    public final boolean isCustomMenu(ItemStack item) {
        if (!(item.hasItemMeta())) {
            return false;
        }
        if (!(item.getItemMeta().hasLocalizedName())) {
            return false;
        }
        if (!(item.getItemMeta().getLocalizedName().equals("menu"))) {
            return false;
        }
        return true;
    }
}
