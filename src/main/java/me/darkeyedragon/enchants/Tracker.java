package me.darkeyedragon.enchants;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import jdk.nashorn.internal.ir.annotations.Immutable;
import me.darkeyedragon.enchants.enchant.CustomEnchantment;
import me.darkeyedragon.enchants.enchant.basic.GillsEnchantment;
import me.darkeyedragon.enchants.enchant.basic.VisionaryEnchantment;
import me.darkeyedragon.enchants.util.EnchantmentUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.function.Consumer;

import static org.bukkit.Bukkit.getServer;

public class Tracker {

    //TODO implement a system to check if their item is still equiped

    private static final Map<Player, Integer> playerVisionaryEquiped;
    private static final Map<Player, Integer> playerGillsEquiped;
    private static final Set<Player> playerReplenishEquiped;
    private static final Map<Player, Integer> playerHaste;
    private static int id;
    private static boolean running;

    static {
        playerVisionaryEquiped = new HashMap<>();
        playerGillsEquiped = new HashMap<>();
        playerReplenishEquiped = new HashSet<>();
        playerHaste = new HashMap<>();
    }


    public static void addVisionary(Player player, int itemSlot) {
        //player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        playerVisionaryEquiped.put(player, itemSlot);
    }

    public static void addGills(Player player, int itemSlot) {
        playerGillsEquiped.put(player, itemSlot);
    }

    public static void addReplenish(Player player) {
        playerReplenishEquiped.add(player);
    }

    public static void addHaste(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level, false, false));
        playerHaste.put(player, level);
    }

    public static void removeVisionary(Player player) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        playerVisionaryEquiped.remove(player);
    }

    public static void removeGills(Player player) {
        player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        playerVisionaryEquiped.remove(player);
    }

    public static void removeReplenish(Player player) {
        playerReplenishEquiped.remove(player);
    }

    public static void removeHaste(Player player) {
        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        playerHaste.remove(player);
    }


    /**
     * Schedules repeating task to trigger enchants that dont have events linked to them.
     *
     * @param plugin Plugin instance
     */
    public static void schedule(Plugin plugin) {
        if (running) return;
        BukkitScheduler scheduler = getServer().getScheduler();
        id = scheduler.scheduleSyncRepeatingTask(plugin, () -> {

            //Add night vision for all players in the list
            //playerVisionaryEquiped.forEach(Tracker::applyVisionary);
            for (Iterator<Player> it = playerVisionaryEquiped.keySet().iterator(); it.hasNext(); ) {
                Player player = it.next();
                ItemStack actualItem = player.getInventory().getHelmet();
                //ItemStack actualItem = armorStacks[8-itemSlot];
                if (EnchantmentUtil.hasEnchantment(actualItem, VisionaryEnchantment.class)) {
                    applyVisionary(player);
                } else {
                    it.remove();
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }
            }

            //Add water breathing for all players in the list
            for (Iterator<Player> it = playerGillsEquiped.keySet().iterator(); it.hasNext(); ) {
                Player player = it.next();
                ItemStack actualItem = player.getInventory().getHelmet();
                //ItemStack actualItem = armorStacks[8-itemSlot];
                if (EnchantmentUtil.hasEnchantment(actualItem, GillsEnchantment.class)) {
                    applyGills(player);
                } else {
                    it.remove();
                    player.removePotionEffect(PotionEffectType.WATER_BREATHING);
                }
            }
            //Set the food level to max for all players in the list
            playerReplenishEquiped.forEach(player -> player.setFoodLevel(20));

            //Apply haste to all players in the list
            playerHaste.forEach((player, level) -> player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, level, false, false)));
        }, 0, 20L);
        running = id > 0;
    }

    private static void applyGills(Player player) {
        Location l = player.getLocation();
        l.setY(l.getY() + 1);
        Block b = l.getBlock();

        if (b.isLiquid()) {
            player.setRemainingAir(player.getMaximumAir());
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false));
        } else {
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        }
    }

    private static void applyVisionary(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
    }

    private static void loopOver(Set<Player> playerSet, Class<CustomEnchantment> customEnchantment, Consumer<Player> consumer) {
        for (Iterator<Player> it = playerSet.iterator(); it.hasNext(); ) {
            Player player = it.next();
            ItemStack actualItem = player.getInventory().getHelmet();
            if (EnchantmentUtil.hasEnchantment(actualItem, customEnchantment)) {
                consumer.accept(player);
            } else {
                it.remove();
                //Do something witht his
                player.removePotionEffect(PotionEffectType.WATER_BREATHING);
            }
        }
    }

    @Immutable
    public static Map<Player, Integer> getPlayerVisionaryEquiped() {
        return ImmutableMap.copyOf(playerVisionaryEquiped);
    }

    @Immutable
    public static Set<Player> getPlayerReplenishEquiped() {
        return ImmutableSet.copyOf(playerReplenishEquiped);
    }

    @Immutable
    public static Map<Player, Integer> getPlayerHaste() {
        return ImmutableMap.copyOf(playerHaste);
    }

    @Immutable
    public static Map<Player, Integer> getPlayerGills() {
        return ImmutableMap.copyOf(playerHaste);
    }

    public static void removeFromAll(Player player) {
        removeVisionary(player);
        removeReplenish(player);
        removeHaste(player);
        removeGills(player);
    }
}
