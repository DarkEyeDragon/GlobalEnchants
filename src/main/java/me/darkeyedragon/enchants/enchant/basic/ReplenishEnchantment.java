package me.darkeyedragon.enchants.enchant.basic;

import me.darkeyedragon.enchants.Tracker;
import me.darkeyedragon.enchants.enchant.CustomArmorEnchantment;
import me.darkeyedragon.enchants.enchant.EquipAction;
import me.darkeyedragon.enchants.event.PlayerArmorChangeEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.potion.PotionEffectType;

public class ReplenishEnchantment extends CustomArmorEnchantment implements EquipAction {

    public ReplenishEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof ReplenishEnchantment;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_HEAD;
    }

    @Override
    public void equip(PlayerArmorChangeEvent event) {
        if (!Tracker.getPlayerReplenishEquiped().contains(event.getPlayer())) {
            Tracker.addReplenish(event.getPlayer());
        }
    }

    @Override
    public void unEquip(PlayerArmorChangeEvent event) {
        if (Tracker.getPlayerReplenishEquiped().contains(event.getPlayer())) {
            Tracker.removeReplenish(event.getPlayer());
        }
    }
}
