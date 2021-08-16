package com.jubiman.dungeons.eventlisteners;

import com.jubiman.dungeons.abilities.abilities;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.lang.reflect.InvocationTargetException;

public class UseAbility implements Listener {
    @EventHandler
    public void onUseAbility(PlayerInteractEvent e) throws InvocationTargetException, IllegalAccessException {
        Player p = e.getPlayer();
        Action a = e.getAction();
        // Return if action is not a right click
        if (e.getAction() == Action.PHYSICAL || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getItem() == null) return;

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(p.getItemInHand());
        NBTTagCompound itemCompound = nmsItem.getTag();
        if (itemCompound != null && itemCompound.hasKey("Ability")) {
            if (abilities.containsKey(itemCompound.getString("Ability"))) {
                abilities.run(itemCompound.getString("Ability"), p);
            }
        }
    }
}
