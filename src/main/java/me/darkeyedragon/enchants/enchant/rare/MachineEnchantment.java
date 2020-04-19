package me.darkeyedragon.enchants.enchant.rare;

import me.darkeyedragon.enchants.Tracker;
import me.darkeyedragon.enchants.enchant.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MachineEnchantment extends CustomEnchantment {
    public MachineEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id, name, description, tierType, startingLevel, maxLevel);
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return other instanceof MachineEnchantment;
    }

    /**
     * Weapons are also targetable
     * @return {@link EnchantmentTarget}
     */
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event){
        ItemStack itemStack = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(itemStack == null || itemStack.getType() == Material.AIR) return;
        AtomicBoolean hasEnchantment = new AtomicBoolean(false);
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        enchantments.forEach((enchantment, level) ->{
            if(enchantment instanceof MachineEnchantment){
                Tracker.addHaste(event.getPlayer(), level);
                hasEnchantment.set(true);
            }
        });
        if(!hasEnchantment.get()){
            Tracker.removeHaste(event.getPlayer());
        }
    }
}
