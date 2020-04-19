package me.darkeyedragon.enchants.enchant;

import me.darkeyedragon.enchants.enchant.basic.*;
import me.darkeyedragon.enchants.enchant.rare.BlazingEnchantment;
import me.darkeyedragon.enchants.enchant.rare.MachineEnchantment;
import me.darkeyedragon.enchants.enchant.rare.MultiShotEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.*;

public abstract class CustomEnchantment extends Enchantment implements Listener {
    private String name;
    private TierType tierType;
    private String description;
    private int startingLevel;
    private int maxLevel;

    public enum TierType {
        BASIC(new Tier(1, "Basic", 20, ChatColor.GREEN)),
        RARE(new Tier(2, "Rare", 35, ChatColor.GOLD)),
        MEGA(new Tier(3, "Mega", 50, ChatColor.DARK_AQUA)),
        ULTRA(new Tier(4, "Ultra", 70, ChatColor.DARK_PURPLE));

        private Tier tier;

        TierType(Tier tier) {
            this.tier = tier;
        }

        public Tier getTier() {
            return tier;
        }
    }

    public enum CustomEnchantmentType {
        //Basic Tier
        GILLS(new GillsEnchantment(100, "Gills", "Applies permanent water breathing effect.", CustomEnchantment.TierType.BASIC, 1,1)),
        IGNITION(new IgnitionEnchantment(101, "Ignition", "Ignites your attacker.", CustomEnchantment.TierType.BASIC,1,3)),
        VISIONARY(new VisionaryEnchantment(102, "Visionary", "Applies permanent night vision effect.", CustomEnchantment.TierType.BASIC,1,1)),
        THOR(new ThorEnchantment(103, "Thor", "Strikes a powerful lightning strike at the hit target. ", CustomEnchantment.TierType.BASIC,1,3)),
        REPLENISH(new ReplenishEnchantment(104, "Replenish", "Prevents hunger loss. ",CustomEnchantment.TierType.BASIC,1,1)),
        JELLO(new JelloEnchantment(105, "Jello", "Prevents fall damage.", CustomEnchantment.TierType.BASIC,1,1)),
        DIZZY(new DizzyEnchantment(106, "Dizzy", "Applies nausea effect to target.", CustomEnchantment.TierType.BASIC,1,5)),


        MULTI_SHOT(new MultiShotEnchantment(107, "Multi Shot", "Shoots multiple arrows at once.", TierType.RARE,1,3)),
        MACHINE(new MachineEnchantment(108, "Machine", "Grants a permanent haste effect.", TierType.RARE,1,3)),
        BLAZING(new BlazingEnchantment(109, "Blazing", "Grants a permanent haste effect.", TierType.RARE,1,3));

        private final CustomEnchantment customEnchantment;

        CustomEnchantmentType(CustomEnchantment customEnchantment){
            this.customEnchantment = customEnchantment;
        }

        public CustomEnchantment get() {
            return customEnchantment;
        }
    }
    public CustomEnchantment(int id, String name, String description, TierType tierType, int startingLevel, int maxLevel) {
        super(id);
        this.name = name;
        this.description = description;
        this.startingLevel = startingLevel;
        this.maxLevel = maxLevel;
        this.tierType = tierType;
    }

    public static void registerEnchant(CustomEnchantment enchantment) {
        if (setAllowRegistration(true)) {
            clearRegisteredEnchant("byId", enchantment.getId());
            clearRegisteredEnchant("byName", enchantment.getName());

            EnchantmentWrapper.registerEnchantment(enchantment);
            setAllowRegistration(false);
        }
    }

    private static boolean setAllowRegistration(boolean b) {
        try {
            Field field = org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);

            field.set(null, b);
            field.setAccessible(false);

            return true;
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    private static <T> void clearRegisteredEnchant(String fieldName, T key) {
        try {
            Field field = org.bukkit.enchantments.Enchantment.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            Map<?, org.bukkit.enchantments.Enchantment> enchantmentMap = (Map<?, org.bukkit.enchantments.Enchantment>) field.get(null);
            field.setAccessible(false);

            enchantmentMap.remove(key);
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    public Tier getTier() {
        return tierType.tier;
    }

    public TierType getTierType() {
        return tierType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return startingLevel;
    }

    public ItemStack addToItem(ItemStack itemStack, int lvl) {
        itemStack.addEnchantment(this, lvl);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(tierType.tier.getChatColor() + getName() + " " + romanNumerals(lvl));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addToItem(ItemStack itemStack, int lvl, CustomEnchantment customEnchantment) {
        return customEnchantment.addToItem(itemStack, lvl);
    }

    private String romanNumerals(int i) {
        StringBuilder numeral = new StringBuilder();

        if (1 <= i && i <= 3) {
            for (int j = 0; j < i; j++)
                numeral.append("I");
        } else if (i == 4)
            numeral.append("IV");
        else if (i == 5)
            numeral.append("V");

        return numeral.toString();
    }

    @Override
    public abstract boolean conflictsWith(Enchantment other);

    @Override
    public boolean canEnchantItem(ItemStack item) {
        for (Enchantment enchantment : item.getEnchantments().keySet()) {
            if (conflictsWith(enchantment)) return false;
        }
        return true;
    }

    @Override
    public abstract EnchantmentTarget getItemTarget();

}
