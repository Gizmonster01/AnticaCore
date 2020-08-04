package me.gizmonster.anticacore.utils;

import me.gizmonster.anticacore.AnticaCorePlugin;
import me.gizmonster.anticacore.enums.WeekDay;
import me.gizmonster.anticacore.objects.Shop;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static me.gizmonster.anticacore.utils.ACConstants.storeOwnerKey;

public class ShopManager {

    private AnticaCorePlugin plugin;

    public List<Shop> shops = new ArrayList<>();

    public HashMap<WeekDay, Shop> weekShop = new HashMap<>();
    public HashMap<UUID, Long> errorMap = new HashMap<>();
    public List<Player> setupList = new ArrayList<>();
    public HashMap<UUID, Double> shopTracker = new HashMap<>();

    public ShopManager(AnticaCorePlugin mainClass) {
        this.plugin = mainClass;
    }

    public void defineNPC(Entity entity) {
        entity.getPersistentDataContainer().set(storeOwnerKey, PersistentDataType.INTEGER, 1);
    }

    public void createShop(Player player, String[] args) {
        Block block = player.getTargetBlock(null, 5);
        if (!(block.getType() == Material.CHEST)) {
            player.sendMessage(ChatColor.RED + "That is not a chest.");
            return;
        }
        String id = args[1];
        String display = args[2];
        Location loc = block.getLocation();
        WeekDay day = WeekDay.valueOf(args[3]);
        if (day == null) {
            player.sendMessage(ChatColor.RED + "Day is not valid");
            return;
        }
        Shop shop = new Shop(id, display, loc, day);
        weekShop.remove(day);
        weekShop.putIfAbsent(day, shop);
        shops.add(shop);
        plugin.file.saveFile();
    }

    public void openTodayShop(Player player) {
        plugin.time.checkWeekday();
        WeekDay today = plugin.time.today;
        if (weekShop.get(today) == null) {
            player.sendMessage(ChatColor.RED + "There is no shop assigned to " + today);
            return;
        }
        Shop shop = weekShop.get(today);
        openShop(player, shop);
    }

    public void openShop(Player player, Shop shop) {
        Chest chest = (Chest) Bukkit.getWorld("spawn").getBlockAt(shop.getLocation()).getState();
        List<ItemStack> contents = Arrays.asList(chest.getInventory().getContents().clone());
        Inventory shopUI = Bukkit.createInventory(null, 5 * 9, "Use Resource Pack Pls");
        int slot = 0;
        for (int i = 1; i < 10; i++) {
            shopUI.setItem(i, plugin.menuItems.shopUIBorder);
        }
        for (int i = 0; i < 10; i++) {
            shopUI.setItem(i + 35, plugin.menuItems.shopUIBorder);
        }
        shopUI.setItem(0, plugin.menuItems.shopUIShell);
        for (ItemStack item : contents) {
            shopUI.setItem(slot + 9, item);
            slot = slot + 1;
        }
        player.openInventory(shopUI);
    }

    public void shopify(Player player, String[] args) {
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You need to hold an item to do that.");
            return;
        }

        ItemStack item = stripShopMeta(player.getInventory().getItemInMainHand());
        ItemMeta meta = item.getItemMeta();

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Arguments needed");
            return;
        }
        if (!(NumberUtils.isNumeric(args[1]))) {
            player.sendMessage(ChatColor.RED + "Price must be a double/integer");
            return;
        }
        double price = Double.parseDouble(args[1]);

        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(ChatColor.of("#c7c7c7") + "" + ChatColor.STRIKETHROUGH + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        lore.add(ChatColor.of("#c7c7c7") + "Price: " + ChatColor.of("#2bd62e") + new DecimalFormat("$#,###.00").format(price));
        lore.add(ChatColor.of("#c7c7c7") + "Amount: " + ChatColor.of("#2bd62e") + item.getAmount());
        lore.add(ChatColor.of("#c7c7c7") + "" + ChatColor.STRIKETHROUGH + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(ACConstants.priceKey, PersistentDataType.DOUBLE, price);
        item.setItemMeta(meta);
    }

    public ItemStack stripShopMeta(ItemStack item) {
        int amt = item.getAmount();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().remove(ACConstants.priceKey);
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (Iterator<String> iterator = lore.iterator(); iterator.hasNext(); ) {
                String value = iterator.next();
                if (value.contains("Price:")) {
                    iterator.remove();
                }
                if (value.contains("Amount:")) {
                    iterator.remove();
                }
                if (value.contains("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯")) {
                    iterator.remove();
                }
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            item.setAmount(amt);
        }
        return item;
    }

    public String getItemName(ItemStack item) {
        if (item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        return StringHelper.capitailizeWord(item.getType().name().replace("_", " ").toLowerCase());
    }

    public void attemptPurchase(Player player, ItemStack item) {
        if (!(item.hasItemMeta())) {
            return;
        }
        if (!(item.getItemMeta().getPersistentDataContainer().has(ACConstants.priceKey, PersistentDataType.DOUBLE))) {
            return;
        }
        double price = item.getItemMeta().getPersistentDataContainer().get(ACConstants.priceKey, PersistentDataType.DOUBLE);
        if (!(plugin.econ.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId())) >= price)) {
            plugin.msg.sendError(player, true, "Insufficient Funds!");
            errorMap.put(player.getUniqueId(), System.currentTimeMillis());
            return;
        }
        if (giveItem(player, stripShopMeta(item.clone()))) {
            plugin.econ.withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), price);
            plugin.msg.sendSuccessPurchase(player, true, "You purchased " + ChatColor.stripColor(getItemName(item)) + " for " + new DecimalFormat("$#,###.00").format(price) + "!");
            Double count = shopTracker.get(player.getUniqueId());
            if (count == null) {
                shopTracker.put(player.getUniqueId(), price);
            }
            else {
                shopTracker.put(player.getUniqueId(), count + price);
            }
        }
    }

    public void sessionCloseMessage(Player player) {
        double totalSpent = plugin.shopManager.shopTracker.get(player.getUniqueId());
        BaseComponent[] component = new ComponentBuilder()
                .append("You spent ").color(ChatColor.of("#f2f1f0"))
                .append(new DecimalFormat("$#,###.00").format(totalSpent) + " ").color(ChatColor.of("#ff860d"))
                .append("total this session.").color(ChatColor.of("#f2f1f0"))
                .create();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        plugin.shopManager.shopTracker.remove(player.getUniqueId());
    }

    public boolean giveItem(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() == -1) {
            plugin.msg.sendError(player, true, "You are out of inventory space!");
            errorMap.put(player.getUniqueId(), System.currentTimeMillis());
            return false;
        }
        player.getInventory().addItem(item);
        return true;
    }
}
