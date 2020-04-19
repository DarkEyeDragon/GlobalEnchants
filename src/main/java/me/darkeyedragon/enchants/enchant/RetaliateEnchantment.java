package me.darkeyedragon.enchants.enchant;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface RetaliateEnchantment {
    void retaliate(EntityDamageByEntityEvent event, int lvl);
}
