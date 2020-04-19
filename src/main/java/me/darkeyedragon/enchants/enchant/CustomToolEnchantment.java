package me.darkeyedragon.enchants.enchant;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.block.BlockBreakEvent;

public abstract class CustomToolEnchantment extends CustomEnchantment{

    public CustomToolEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }

    @Override
    public abstract boolean conflictsWith(Enchantment other);

    @Override
    public abstract EnchantmentTarget getItemTarget();

    public abstract void blockBreak(BlockBreakEvent event);
}
