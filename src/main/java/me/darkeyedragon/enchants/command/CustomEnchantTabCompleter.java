package me.darkeyedragon.enchants.command;

import me.darkeyedragon.enchants.Enchants;
import me.darkeyedragon.enchants.enchant.CustomEnchantment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomEnchantTabCompleter implements TabCompleter {

    Enchants enchants;

    public CustomEnchantTabCompleter(Enchants enchants) {
        this.enchants = enchants;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("ce") && args.length > 0) {
            if (sender instanceof Player) {
                return Arrays.stream(CustomEnchantment.CustomEnchantmentType.values()).map(customEnchantmentType -> customEnchantmentType.get().getName()).collect(Collectors.toList());
            }
        }
        return null;
    }
}
