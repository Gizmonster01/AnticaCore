package me.gizmonster.anticacore.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R1.util.UnsafeList;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ShopKeeper extends EntityVillager {

    public ShopKeeper(Location loc) {
        super(EntityTypes.VILLAGER, ((CraftWorld) loc.getWorld()).getHandle());

        this.setPosition(loc.getX(),loc.getY(),loc.getZ());

        this.setCustomNameVisible(false);
        this.setHealth(20);
        this.setInvulnerable(true);

        this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    }
}
