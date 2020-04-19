package me.darkeyedragon.enchants.enchant.basic;

import me.darkeyedragon.enchants.enchant.ChanceBased;
import me.darkeyedragon.enchants.enchant.CustomWeaponEnchantment;
import me.darkeyedragon.enchants.event.EntityDamagedByEnchantedWeaponEvent;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ThorEnchantment extends CustomWeaponEnchantment implements ChanceBased {

    private ThreadLocalRandom random;

    public ThorEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
        random = ThreadLocalRandom.current();
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof ThorEnchantment;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public void hitTarget(Player source, Entity target, ItemStack weapon, CustomWeaponEnchantment weaponEnchantment, int enchantmentLevel) {
        if (triggerEffect(enchantmentLevel)) {
            Location location = target.getLocation();
            location.getWorld().strikeLightningEffect(location);
            ((Damageable) target).damage(2, source);
        }
    }

    @Override
    public boolean triggerEffect(int lvl) {
        int r = random.nextInt(0, 100);
        return r < 5 * lvl;
    }

}
