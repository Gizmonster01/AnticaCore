package me.gizmonster.anticacore.resources;

import me.gizmonster.anticacore.AnticaCorePlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuItems {

    private AnticaCorePlugin plugin;

    public MenuItems(AnticaCorePlugin plugin) {
        this.plugin = plugin;
        initializeItemMeta();
    }
    public ItemStack shopUIShell = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    public ItemStack shopUIBorder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

    public void initializeItemMeta() {
        ItemMeta shopShellMeta = shopUIShell.getItemMeta();
        shopShellMeta.setCustomModelData(99);
        shopShellMeta.setDisplayName(" ");
        shopShellMeta.setLocalizedName("menu");
        shopUIShell.setItemMeta(shopShellMeta);

        ItemMeta shopBorderMeta = shopUIBorder.getItemMeta();
        shopBorderMeta.setDisplayName(" ");
        shopBorderMeta.setCustomModelData(98);
        shopUIBorder.setItemMeta(shopBorderMeta);
    }
}
