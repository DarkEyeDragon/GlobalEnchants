package me.darkeyedragon.enchants.enchant;

import me.darkeyedragon.enchants.event.PlayerArmorChangeEvent;

public interface EquipAction {
    void equip(PlayerArmorChangeEvent event);

    void unEquip(PlayerArmorChangeEvent event);
}
