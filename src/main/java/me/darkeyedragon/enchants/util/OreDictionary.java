package me.darkeyedragon.enchants.util;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class OreDictionary {
    private static final Map<Material, Material> dictionary;
    static{
        dictionary = new HashMap<>();
        dictionary.put(Material.IRON_ORE, Material.IRON_INGOT);
        dictionary.put(Material.GOLD_ORE, Material.GOLD_INGOT);
    }

    public static Material getAsMolten(Material material){
        return dictionary.get(material);
    }
    public static boolean contains(Material material){
        return dictionary.containsKey(material);
    }
}
