package me.darkeyedragon.enchants.listener.bukkit;

import me.darkeyedragon.enchants.Enchants;
import me.darkeyedragon.enchants.Tracker;
import me.darkeyedragon.enchants.enchant.CustomEnchantment;
import me.darkeyedragon.enchants.enchant.basic.ReplenishEnchantment;
import me.darkeyedragon.enchants.enchant.basic.VisionaryEnchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerJoinQuitListener implements Listener {

    Enchants plugin;

    public PlayerJoinQuitListener(Enchants plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        PlayerInventory inventory = event.getPlayer().getInventory();
        ItemStack[] items = inventory.getArmorContents();
        CustomEnchantment.CustomEnchantmentType[] enchantTypes = CustomEnchantment.CustomEnchantmentType.values();
        for (ItemStack item : items) {
            item.getEnchantments().keySet().forEach(enchantment -> {
                for (CustomEnchantment.CustomEnchantmentType enchantType : enchantTypes) {
                    if(enchantType.get() instanceof VisionaryEnchantment){
                        Tracker.addVisionary(event.getPlayer(), inventory.first(item));
                    }else if(enchantType.get() instanceof ReplenishEnchantment){
                        Tracker.addReplenish(event.getPlayer());
                    }
                }
            });
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Tracker.removeFromAll(event.getPlayer());
    }
}
