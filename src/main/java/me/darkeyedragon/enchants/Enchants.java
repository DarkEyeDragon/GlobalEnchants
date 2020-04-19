package me.darkeyedragon.enchants;

import me.darkeyedragon.enchants.command.CustomEnchantmentCommand;
import me.darkeyedragon.enchants.enchant.CustomEnchantment;
import me.darkeyedragon.enchants.listener.bukkit.EntityDamageByEntityListener;
import me.darkeyedragon.enchants.listener.bukkit.InventoryClickListener;
import me.darkeyedragon.enchants.listener.custom.PlayerArmorChangeListener;
import me.darkeyedragon.enchants.listener.bukkit.PlayerJoinQuitListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public final class Enchants extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("ce").setExecutor(new CustomEnchantmentCommand(this));
        //getCommand("ce").setTabCompleter(new CustomEnchantTabCompleter(this));
        Tracker.schedule(this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerArmorChangeListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        for (CustomEnchantment.CustomEnchantmentType type : CustomEnchantment.CustomEnchantmentType.values()) {
            CustomEnchantment.registerEnchant(type.get());
            this.getServer().getPluginManager().registerEvents(type.get(), this);
        }

    }

    @Override
    public void onDisable() {

    }
}
