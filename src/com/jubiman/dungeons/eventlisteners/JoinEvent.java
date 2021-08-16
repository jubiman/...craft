package com.jubiman.dungeons.eventlisteners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		{
			Player p = e.getPlayer();
			e.setJoinMessage(p.getDisplayName() + ChatColor.YELLOW + " has joined! Stop being cringe lol");
		}
	}
}
