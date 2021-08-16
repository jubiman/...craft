package com.jubiman.dungeons;

import com.jubiman.dungeons.abilities.abilities;
import com.jubiman.dungeons.commands.CommandDungeons;
import com.jubiman.dungeons.eventlisteners.JoinEvent;
import com.jubiman.dungeons.eventlisteners.UseAbility;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dungeons extends JavaPlugin {
    @Override
    public void onEnable(){
        registerEvents();
        this.getCommand("dungeons").setExecutor(new CommandDungeons());
        try {
            abilities.initialize();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable(){
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new UseAbility(), this);
    }
}
