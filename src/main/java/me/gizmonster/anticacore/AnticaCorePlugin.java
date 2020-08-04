package me.gizmonster.anticacore;

import me.gizmonster.anticacore.commands.LocalizedName;
import me.gizmonster.anticacore.commands.ShopCommand;
import me.gizmonster.anticacore.listeners.InventoryClose;
import me.gizmonster.anticacore.listeners.MenuClick;
import me.gizmonster.anticacore.listeners.NPCClick;
import me.gizmonster.anticacore.resources.MenuItems;
import me.gizmonster.anticacore.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class AnticaCorePlugin extends JavaPlugin {

    private static AnticaCorePlugin instance;
    public FileManager file;
    public ShopManager shopManager;
    public TimeUtils time;
    public InventoryUtils inventoryUtils;
    public Economy econ;
    public MessagingUtils msg;
    public MenuItems menuItems;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        setupEconomy();
        this.instance = this;
        this.shopManager = new ShopManager(this);
        this.time = new TimeUtils(this);
        this.inventoryUtils = new InventoryUtils();
        this.file = new FileManager(this);
        this.msg = new MessagingUtils();
        this.menuItems = new MenuItems(this);

        getCommand("ashop").setExecutor(new ShopCommand(this));
        getCommand("localizedname").setExecutor(new LocalizedName());

        getServer().getPluginManager().registerEvents(new MenuClick(this), this);
        getServer().getPluginManager().registerEvents(new NPCClick(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(this), this);
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static AnticaCorePlugin getInstance() {
        return instance;
    }

}
