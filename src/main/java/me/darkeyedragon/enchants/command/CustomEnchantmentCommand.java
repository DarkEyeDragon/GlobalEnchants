package me.darkeyedragon.enchants.command;

import me.darkeyedragon.enchants.Enchants;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.darkeyedragon.enchants.enchant.CustomEnchantment.CustomEnchantmentType;

public class CustomEnchantmentCommand implements CommandExecutor {

    private final Enchants plugin;

    public CustomEnchantmentCommand(Enchants plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("ce.gui") && sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack itemStack = new ItemStack(Material.IRON_HELMET);
            ItemStack itemStack2 = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemStack itemStack3 = new ItemStack(Material.BOW);
            ItemStack itemStack4 = new ItemStack(Material.DIAMOND_BOOTS);
            ItemStack itemStack5 = new ItemStack(Material.DIAMOND_AXE);
            ItemStack itemStack6 = new ItemStack(Material.BOW);
            ItemStack itemStack7 = new ItemStack(Material.DIAMOND_PICKAXE);
            itemStack = CustomEnchantmentType.GILLS.get().addToItem(itemStack, 1);
            itemStack = CustomEnchantmentType.VISIONARY.get().addToItem(itemStack, 1);
            itemStack = CustomEnchantmentType.REPLENISH.get().addToItem(itemStack, 1);
            itemStack2 = CustomEnchantmentType.IGNITION.get().addToItem(itemStack2, 3);
            itemStack3 = CustomEnchantmentType.THOR.get().addToItem(itemStack3, 3);
            itemStack4 = CustomEnchantmentType.JELLO.get().addToItem(itemStack4, 1);
            itemStack5 = CustomEnchantmentType.DIZZY.get().addToItem(itemStack5, 5);
            itemStack6 = CustomEnchantmentType.MULTI_SHOT.get().addToItem(itemStack6, 1);
            itemStack5 = CustomEnchantmentType.MACHINE.get().addToItem(itemStack5, 1);
            itemStack7 = CustomEnchantmentType.BLAZING.get().addToItem(itemStack7, 1);
            player.getInventory().addItem(itemStack);
            player.getInventory().addItem(itemStack2);
            player.getInventory().addItem(itemStack3);
            player.getInventory().addItem(itemStack4);
            player.getInventory().addItem(itemStack5);
            player.getInventory().addItem(itemStack6);
            player.getInventory().addItem(itemStack7);
            return true;
        }
        return false;
    }
}
