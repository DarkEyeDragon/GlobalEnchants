package me.darkeyedragon.enchants.enchant.basic;

import me.darkeyedragon.enchants.enchant.ChanceBased;
import me.darkeyedragon.enchants.enchant.CustomWeaponEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class DizzyEnchantment extends CustomWeaponEnchantment implements ChanceBased {

    private final ThreadLocalRandom random;

    public DizzyEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
        random = ThreadLocalRandom.current();
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof DizzyEnchantment;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item.getType().name().endsWith("SWORD") || item.getType().name().endsWith("AXE")) {
            return super.canEnchantItem(item);
        }
        return false;
    }

    @Override
    public void hitTarget(Player source, Entity target, ItemStack weapon, CustomWeaponEnchantment weaponEnchantment, int enchantmentLevel) {
        if(target instanceof Player){
            if(triggerEffect(enchantmentLevel)){
                int lvl = enchantmentLevel < 4 ? 1 : 2;
                ((Player)target).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, lvl, false, true));
            }
        }
    }

    @Override
    public boolean triggerEffect(int lvl) {
        int chance;
        switch (lvl){
            case 1:
            case 4:
                chance = 5;
                break;
            case 2:
            case 5:
                chance = 8;
                break;
            case 3:
                chance = 12;
                break;
            default:
                return false;
        }
        int r = random.nextInt(0, 100);
        return r < chance;
    }
}
