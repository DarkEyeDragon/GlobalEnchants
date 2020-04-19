package me.darkeyedragon.enchants.enchant;

/**
 * Custem enchantment that needs to execute specific equip and unequip
 */
public abstract class CustomArmorEnchantment extends CustomEnchantment {

    public CustomArmorEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }
}
