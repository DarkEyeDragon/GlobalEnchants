package me.darkeyedragon.enchants.listener.custom;

import me.darkeyedragon.enchants.event.ItemEnchantEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemEnchantListener implements Listener {

    @EventHandler
    public void onItemEnchant(ItemEnchantEvent event){


        /*try{
            enchMap.forEach(item::addEnchantment);
            originalEnchMap.forEach(item::addEnchantment);
        }catch (IllegalArgumentException exception){
            event.getWhoClicked().sendMessage("These enchantments are not compatible!");
        }*/
    }
}
