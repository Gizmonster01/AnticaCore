package me.gizmonster.anticacore.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.gizmonster.anticacore.AnticaCorePlugin;
import me.gizmonster.anticacore.objects.Shop;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private AnticaCorePlugin plugin;
    private final String fileName = "shop-data.json";

    public FileManager(AnticaCorePlugin mainClass) {
        this.plugin = mainClass;
        loadFile();
    }

    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public void loadFile() {
        File followersFile = new File(plugin.getDataFolder(), fileName);

        if(followersFile.exists()) {
            Reader reader = null;
            try {
                reader = new FileReader(followersFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Shop[] shopList = gson.fromJson(reader, Shop[].class);
            for(Shop shop : shopList) {
                plugin.shopManager.shops.add(shop);
                if (!(shop.getDay() == null)) {
                    plugin.shopManager.weekShop.put(shop.getDay(), shop);
                }
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile() {
        File shopData = new File(plugin.getDataFolder(), fileName);

        if(!shopData.exists()) {
            shopData.getParentFile().mkdirs();
        }
        if(!shopData.exists()) {
            try {
                shopData.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Writer writer = null;
        try {
            writer = new FileWriter(shopData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(plugin.shopManager.shops, writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
