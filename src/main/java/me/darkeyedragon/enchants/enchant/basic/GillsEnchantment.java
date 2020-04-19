package me.darkeyedragon.enchants.enchant.basic;

import me.darkeyedragon.enchants.Tracker;
import me.darkeyedragon.enchants.enchant.CustomArmorEnchantment;
import me.darkeyedragon.enchants.enchant.EquipAction;
import me.darkeyedragon.enchants.event.PlayerArmorChangeEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;

public class GillsEnchantment extends CustomArmorEnchantment implements EquipAction {

    public GillsEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof GillsEnchantment;
    }

    @Override
    public void equip(PlayerArmorChangeEvent event) {
        Tracker.addGills(event.getPlayer(), event.getSlot());
    }

    @Override
    public void unEquip(PlayerArmorChangeEvent event) {
        Tracker.removeGills(event.getPlayer());
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_HEAD;
    }

}
