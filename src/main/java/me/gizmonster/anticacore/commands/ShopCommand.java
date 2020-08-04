package me.gizmonster.anticacore.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import me.gizmonster.anticacore.AnticaCorePlugin;
import me.gizmonster.anticacore.entities.ShopKeeper;
import me.gizmonster.anticacore.objects.Shop;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftMob;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;

import static me.gizmonster.anticacore.utils.ACConstants.storeOwnerKey;

public class ShopCommand implements CommandExecutor {

    private AnticaCorePlugin plugin;
    public ShopCommand(AnticaCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        Location loc = player.getLocation();
        if (args[0].equals("block")) {
            Bukkit.broadcastMessage(player.getTargetBlock(null, 5).getType().toString());
            return true;
        }
        if (args[0].equals("summon")) {
            Mob entity = (Mob) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            Villager village = (Villager) entity;
            village.setVillagerType(Villager.Type.PLAINS);
            entity.setCollidable(false);
            ItemStack oldmanGlasses = new ItemStack(Material.STICK);
            ItemMeta meta = oldmanGlasses.getItemMeta();
            meta.setCustomModelData(1);
            oldmanGlasses.setItemMeta(meta);
            entity.getEquipment().setHelmet(oldmanGlasses);
            entity.getPersistentDataContainer().set(storeOwnerKey, PersistentDataType.INTEGER, 1);
            entity.setCustomName(ChatColor.of("#fc000d") + "Shopkeeper");
            entity.setInvulnerable(true);
            entity.setRemoveWhenFarAway(false);
            clearGoals(entity, 10);
            return true;
        }
        if (args[0].equals("npc")) {
            plugin.shopManager.setupList.add(player);
            return true;
        }
        if (args[0].equals("set")) {
            plugin.shopManager.createShop(player, args);
            return true;
        }
        if (args[0].equals("today")) {
            plugin.shopManager.openTodayShop(player);
            return true;
        }
        if (args[0].equals("shopify")) {
            plugin.shopManager.shopify(player, args);
        }
        return false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void clearGoals(Mob mob, int lookDistance) {
        try {
            EntityInsentient creature = ((CraftMob) mob).getHandle();

            //Clears goals and targets
            creature.goalSelector = new PathfinderGoalSelector(creature.world.getMethodProfilerSupplier());
            creature.targetSelector = new PathfinderGoalSelector(creature.world.getMethodProfilerSupplier());

            //Add Human looking pathfinder
            creature.goalSelector.a(0, new PathfinderGoalInteract(creature, EntityHuman.class, lookDistance, 1));

            //Clear jobs
            Field field = EntityLiving.class.getDeclaredField("bn");
            field.setAccessible(true);
            DynamicOpsNBT dynamicopsnbt = DynamicOpsNBT.a;
            field.set(creature, BehaviorController.a(ImmutableList.of(), ImmutableList.of()).a(new Dynamic(dynamicopsnbt, dynamicopsnbt.createMap(ImmutableMap.of(dynamicopsnbt.createString("memories"), dynamicopsnbt.emptyMap())))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
