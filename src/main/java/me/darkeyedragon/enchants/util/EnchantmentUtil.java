package me.darkeyedragon.enchants.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnchantmentUtil {

    public static Pair<Enchantment, Integer> getEnchantment(ItemStack itemStack, Class<? extends Enchantment> enchantment){
        if(itemStack == null) return null;
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantmentItem = entry.getKey();
            if (enchantmentItem.getClass().isAssignableFrom(enchantment)) {
                return new Pair<>(enchantmentItem, entry.getValue());
            }
        }
        return null;
    }

    public static boolean hasEnchantment(ItemStack itemStack, Class<? extends Enchantment> enchantment) {
        if(itemStack == null) return false;
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantmentItem = entry.getKey();
            if (enchantmentItem.getClass().isAssignableFrom(enchantment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param itemStack the {@link ItemStack}
     * @param enchantment the {@link Enchantment}
     * @return the enchantment level if not found -1
     */
    public static int getEnchantmentLevel(ItemStack itemStack, Class<? extends Enchantment> enchantment){
        Pair<Enchantment, Integer> enchantmentItem = getEnchantment(itemStack, enchantment);
        if(enchantmentItem != null){
            return enchantmentItem.getRight();
        }
        return -1;
    }
}
