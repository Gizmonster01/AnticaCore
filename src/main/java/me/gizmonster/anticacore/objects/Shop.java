package me.gizmonster.anticacore.objects;

import me.gizmonster.anticacore.enums.WeekDay;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Shop {

    private String id;
    private String display;

    private WeekDay day;

    private String world;
    private int x;
    private int y;
    private int z;

    private int rows;

    public Shop(String name, String displayname, Location loc, WeekDay day) {
        this.id = name;
        this.display = displayname;
        this.world = loc.getWorld().getName();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();

        this.day = day;
    }

    // Getters
    public Location getLocation() {
        if (Bukkit.getWorld(world) == null) {
            return null;
        }
        Location newLoc = new Location(Bukkit.getWorld(world), x, y, z);
        return newLoc;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
    }
}
