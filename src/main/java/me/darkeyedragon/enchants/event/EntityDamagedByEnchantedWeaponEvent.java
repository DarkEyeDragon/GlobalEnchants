package me.darkeyedragon.enchants.event;

import me.darkeyedragon.enchants.enchant.CustomWeaponEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EntityDamagedByEnchantedWeaponEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player source;
    private final Entity target;
    private final ItemStack weapon;
    private final int enchantmentLevel;
    private final CustomWeaponEnchantment weaponEnchantment;

    public EntityDamagedByEnchantedWeaponEvent(Player source, Entity target, ItemStack weapon, CustomWeaponEnchantment weaponEnchantment, int enchantmentLevel) {
        this.source = source;
        this.target = target;
        this.weapon = weapon;
        this.enchantmentLevel = enchantmentLevel;
        this.weaponEnchantment = weaponEnchantment;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getSource() {
        return source;
    }

    public Entity getTarget() {
        return target;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public int getEnchantmentLevel() {
        return enchantmentLevel;
    }

    public CustomWeaponEnchantment getWeaponEnchantment() {
        return weaponEnchantment;
    }
}
