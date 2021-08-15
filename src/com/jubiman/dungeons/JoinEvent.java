package com.jubiman.dungeons;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        {
            Player p = event.getPlayer();
            event.setJoinMessage(p.getDisplayName() + ChatColor.YELLOW + " has joined! Stop being cringe lol");
        }
    }
}
