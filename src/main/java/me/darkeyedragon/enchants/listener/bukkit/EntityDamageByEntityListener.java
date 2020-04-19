package me.darkeyedragon.enchants.listener.bukkit;

import me.darkeyedragon.enchants.enchant.CustomWeaponEnchantment;
import me.darkeyedragon.enchants.enchant.RetaliateEnchantment;
import me.darkeyedragon.enchants.event.EntityDamagedByEnchantedWeaponEvent;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Map;

public class EntityDamageByEntityListener implements Listener {


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow) {
            ProjectileSource source = ((Arrow) event.getDamager()).getShooter();
            if (source instanceof Player) {
                Player player = (Player) source;
                ItemStack weapon = player.getPlayer().getItemInHand();

                weapon.getEnchantments().forEach((enchantment, lvl) -> {
                    if (enchantment instanceof CustomWeaponEnchantment) {
                        CustomWeaponEnchantment weaponEnchant = (CustomWeaponEnchantment) enchantment;
                        weaponEnchant.hitTarget(player, event.getEntity(), weapon, weaponEnchant, lvl);
                    }
                });
            }
        }else if(event.getEntity() instanceof  Player){
            Player player = (Player)event.getEntity();
            ItemStack[] armor = player.getInventory().getArmorContents();
            for (ItemStack stack : armor) {
                Map<Enchantment, Integer> enchantmentSet = stack.getEnchantments();
                enchantmentSet.forEach((enchantment, lvl) -> {
                    if (enchantment instanceof RetaliateEnchantment) {
                        RetaliateEnchantment retaliateEnchantment = (RetaliateEnchantment) enchantment;
                        retaliateEnchantment.retaliate(event, lvl);
                    }
                });
            }
        }
        if(event.getDamager() instanceof Player){
            Player player = (Player)event.getDamager();
            ItemStack weapon = player.getPlayer().getItemInHand();
            weapon.getEnchantments().forEach((enchantment, lvl) -> {
                if (enchantment instanceof CustomWeaponEnchantment) {
                    CustomWeaponEnchantment weaponEnchant = (CustomWeaponEnchantment) enchantment;
                    weaponEnchant.hitTarget(player, event.getEntity(), weapon, weaponEnchant, lvl);
                }
            });
        }
    }
}
