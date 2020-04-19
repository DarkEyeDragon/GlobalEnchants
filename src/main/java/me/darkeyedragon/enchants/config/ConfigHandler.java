package me.darkeyedragon.enchants.config;

import me.darkeyedragon.enchants.Enchants;
import org.bukkit.ChatColor;

public class ConfigHandler {

    private Enchants plugin;
    private long cooldown = -1;

    public ConfigHandler(Enchants plugin) {
        this.plugin = plugin;
    }

    public String getInitMessage() {
        String message = plugin.getConfig().getString("message.initteleport");
        if (message != null)
            message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }


}
