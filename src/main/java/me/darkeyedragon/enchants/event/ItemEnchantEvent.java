package me.darkeyedragon.enchants.event;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ItemEnchantEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final ItemStack leftItem;
    private final ItemStack centerItem;
    private final ItemStack rightItem;
    private final Map<Enchantment, Integer> originalEnchantments;
    private final Map<Enchantment, Integer> resultEnchantments;


    public ItemEnchantEvent(ItemStack leftItem, ItemStack centerItem, ItemStack rightItem, Map<Enchantment, Integer> originalEnchantments, Map<Enchantment, Integer> resultEnchantments) {
        this.leftItem = leftItem;
        this.centerItem = centerItem;
        this.rightItem = rightItem;
        this.originalEnchantments = originalEnchantments;
        this.resultEnchantments = resultEnchantments;
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

    @Nullable
    public ItemStack getLeftItem() {
        return leftItem;
    }

    @Nullable
    public ItemStack getCenterItem() {
        return centerItem;
    }
    @Nullable

    public ItemStack getRightItem() {
        return rightItem;
    }

    public Map<Enchantment, Integer> getOriginalEnchantments() {
        return originalEnchantments;
    }

    public Map<Enchantment, Integer> getResultEnchantments() {
        return resultEnchantments;
    }
}
