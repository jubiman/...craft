package com.jubiman.dungeons;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dungeons extends JavaPlugin {
    @Override
    public void onEnable(){
        registerEvents();
        this.getCommand("dungeons").setExecutor(new CommandDungeons());
    }

    @Override
    public void onDisable(){
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
    }
}
