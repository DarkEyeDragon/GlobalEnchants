package me.darkeyedragon.enchants.listener.bukkit;

import me.darkeyedragon.enchants.event.PlayerArmorChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory() instanceof PlayerInventory) {
            ItemStack oldItem = event.getCurrentItem();
            ItemStack newItem = event.getCursor();
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                Material material;
                if (oldItem.getType() != Material.AIR) {
                    material = oldItem.getType();
                } else {
                    material = newItem.getType();
                }
                if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                    PlayerArmorChangeEvent.SlotType slotType = PlayerArmorChangeEvent.SlotType.getByMaterial(material);
                    if (slotType != null) {
                        Bukkit.getPluginManager().callEvent(new PlayerArmorChangeEvent(player, slotType, oldItem, newItem, event.getSlot()));
                    }
                } else {
                    PlayerArmorChangeEvent.SlotType slotType = PlayerArmorChangeEvent.SlotType.getByMaterial(material);
                    if (slotType != null && event.isShiftClick()) {
                        Bukkit.getPluginManager().callEvent(new PlayerArmorChangeEvent(player, slotType, newItem, oldItem, event.getSlot()));
                    }
                }
            }
        }
        Inventory inv = event.getInventory();
        // see if the event is about an anvil
        if (inv instanceof AnvilInventory) {
            InventoryView view = event.getView();
            AnvilInventory anvilInventory = (AnvilInventory) inv;
            int rawSlot = event.getRawSlot();
            int originalSlot = 0;
            int enchantSlot = 1;
            // compare the raw slot with the inventory view to make sure we are talking about the upper inventory
            if (rawSlot == view.convertSlot(rawSlot)) {
                /*
                /*slot 0 = left item slot
                slot 1 = right item slot
                slot 2 = result item slot

                see if the player clicked in the result item slot of the anvil inventory*/
                if (rawSlot == 2) {
                    /*get the current item in the result slot
                    I think inv.getItem(rawSlot) would be possible too*/
                    ItemStack item = event.getCurrentItem();

                    ItemStack original = anvilInventory.getItem(originalSlot);
                    ItemStack enchantment = anvilInventory.getItem(enchantSlot);

                    //If there are not enchantments, well then we're done here
                    if(original == null || enchantment == null) return;

                    Map<Enchantment, Integer> originalEnchMap = original.getEnchantments();
                    Map<Enchantment, Integer> enchMap = enchantment.getEnchantments();
                    if(originalEnchMap == null || enchMap == null) return;
                    try{
                        enchMap.forEach(item::addEnchantment);
                        originalEnchMap.forEach(item::addEnchantment);
                    }catch (IllegalArgumentException exception){
                        event.getWhoClicked().sendMessage("These enchantments are not compatible!");
                    }

                }
            }
        }
    }

    @EventHandler
    public void onRightClickArmor(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PlayerArmorChangeEvent.SlotType slotType = PlayerArmorChangeEvent.SlotType.getByMaterial(event.getMaterial());
            if (slotType != null) {
                Player player = event.getPlayer();
                PlayerInventory inventory = player.getInventory();
                boolean setItem = false;
                switch (slotType) {
                    case HEAD:
                        setItem = inventory.getHelmet() == null || inventory.getHelmet().getType() == Material.AIR;
                        break;
                    case CHEST:
                        setItem = inventory.getChestplate() == null || inventory.getChestplate().getType() == Material.AIR;
                        break;
                    case LEGS:
                        setItem = inventory.getLeggings() == null || inventory.getLeggings().getType() == Material.AIR;
                        break;
                    case FEET:
                        setItem = inventory.getBoots() == null || inventory.getBoots().getType() == Material.AIR;
                        break;
                }
                if (setItem) {
                    Bukkit.getPluginManager().callEvent(new PlayerArmorChangeEvent(event.getPlayer(), slotType, new ItemStack(Material.AIR), event.getItem(), inventory.getHeldItemSlot()));
                }
            }
        }
    }
}
