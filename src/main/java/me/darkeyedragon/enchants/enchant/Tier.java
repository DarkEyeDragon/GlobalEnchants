package me.darkeyedragon.enchants.enchant;

import org.bukkit.ChatColor;

public class Tier {
    private int order;
    private String name;
    private int lvlsRequired;
    private ChatColor chatColor;

    public Tier(int order, String name, int lvlsRequired, ChatColor chatColor) {
        this.order = order;
        this.name = name;
        this.lvlsRequired = lvlsRequired;
        this.chatColor = chatColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLvlsRequired() {
        return lvlsRequired;
    }

    public void setLvlsRequired(int lvlsRequired) {
        this.lvlsRequired = lvlsRequired;
    }

    public String toLoreString(){
        return chatColor + name;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
