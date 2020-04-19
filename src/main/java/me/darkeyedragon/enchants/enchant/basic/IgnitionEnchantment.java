package me.darkeyedragon.enchants.enchant.basic;

import me.darkeyedragon.enchants.enchant.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class IgnitionEnchantment extends CustomArmorEnchantment implements RetaliateEnchantment {

    public IgnitionEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id,name, description, tierType,startingLevel, maxLevel);
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof IgnitionEnchantment;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_TORSO;
    }

    @Override
    public void retaliate(EntityDamageByEntityEvent event, int lvl) {
        if(event.getEntity() instanceof Player){
            event.getDamager().setFireTicks(3*lvl*20);
        }
    }
}
