package me.darkeyedragon.enchants.listener.custom;

import me.darkeyedragon.enchants.enchant.CustomArmorEnchantment;
import me.darkeyedragon.enchants.enchant.EquipAction;
import me.darkeyedragon.enchants.event.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerArmorChangeListener implements Listener {

    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent equipEvent) {
        ItemStack newItem = equipEvent.getNewItem();
        ItemStack oldItem = equipEvent.getOldItem();
        if (newItem != null && newItem.getType() != Material.AIR) {
            Map<Enchantment, Integer> enchantMap = newItem.getEnchantments();
            enchantMap.keySet().forEach(key -> {

                if (key instanceof EquipAction) {
                    EquipAction equipAction = (EquipAction) key;
                    equipAction.equip(equipEvent);
                }
            });
        } else {
            //ItemStack oldItem = equipEvent.getOldItem();
            if (oldItem == null || oldItem.getType() == null) return;
            Map<Enchantment, Integer> enchantMap = oldItem.getEnchantments();
            enchantMap.keySet().forEach(key -> {
                if (key instanceof EquipAction) {
                    EquipAction equipAction = (EquipAction) key;
                    equipAction.unEquip(equipEvent);
                }
            });
        }
    }
}
