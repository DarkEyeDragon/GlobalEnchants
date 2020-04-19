package me.darkeyedragon.enchants.enchant.basic;

import me.darkeyedragon.enchants.enchant.CustomArmorEnchantment;
import me.darkeyedragon.enchants.enchant.EquipAction;
import me.darkeyedragon.enchants.event.PlayerArmorChangeEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class JelloEnchantment extends CustomArmorEnchantment {

    public JelloEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof  JelloEnchantment;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL){
            Player player = (Player) event.getEntity();
            Map<Enchantment, Integer> enchantments = player.getInventory().getBoots().getEnchantments();
            enchantments.forEach((enchantment, integer) -> {
                if(enchantment instanceof JelloEnchantment){
                    event.setCancelled(true);
                }
            });
        }
    }
}
