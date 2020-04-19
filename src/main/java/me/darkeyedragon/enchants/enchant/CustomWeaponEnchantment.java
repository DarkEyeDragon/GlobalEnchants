package me.darkeyedragon.enchants.enchant;

import me.darkeyedragon.enchants.event.EntityDamagedByEnchantedWeaponEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public abstract class CustomWeaponEnchantment extends CustomEnchantment {

    public CustomWeaponEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }

    @Override
    public abstract boolean conflictsWith(Enchantment other);

    @Override
    public abstract EnchantmentTarget getItemTarget();

    public abstract void hitTarget(Player source, Entity target, ItemStack weapon, CustomWeaponEnchantment weaponEnchantment, int enchantmentLevel);


}
