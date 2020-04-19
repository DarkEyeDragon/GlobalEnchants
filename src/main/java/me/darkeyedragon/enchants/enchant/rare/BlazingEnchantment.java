package me.darkeyedragon.enchants.enchant.rare;

import me.darkeyedragon.enchants.enchant.ChanceBased;
import me.darkeyedragon.enchants.enchant.CustomToolEnchantment;
import me.darkeyedragon.enchants.util.EnchantmentUtil;
import me.darkeyedragon.enchants.util.OreDictionary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class BlazingEnchantment extends CustomToolEnchantment implements ChanceBased {

    ThreadLocalRandom random;

    public BlazingEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
        random = ThreadLocalRandom.current();
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof BlazingEnchantment;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item.getType().name().endsWith("PICKAXE")) {
            return super.canEnchantItem(item);
        }
        return false;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        ItemStack itemStack = event.getPlayer().getItemInHand();
        int lvl = EnchantmentUtil.getEnchantmentLevel(itemStack, BlazingEnchantment.class);
        if(triggerEffect(lvl)){
            Material material = event.getBlock().getType();
            if(OreDictionary.contains(material)){
                Location loc = event.getBlock().getLocation();
                loc.getWorld().dropItemNaturally(loc, new ItemStack(OreDictionary.getAsMolten(material)));
                loc.getWorld().spawn(loc, ExperienceOrb.class).setExperience(event.getExpToDrop());
                loc.getBlock().setType(Material.AIR);
                event.setCancelled(true);
            }
        }
    }

    @Override
    public boolean triggerEffect(int lvl) {
        int result = random.nextInt(0, 100);
        int chance;
        switch (lvl) {
            case 1:
                chance = 25;
                break;
            case 2:
                chance = 50;
                break;
            case 3:
                chance = 100;
                break;
            default:
                chance = 0;
                break;
        }
        return result < chance;
    }
}
