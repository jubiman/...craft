package com.jubiman.dungeons.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class abilities {
    private static final HashMap<String, Method> abilities = new HashMap<>();

    public static void initialize() throws NoSuchMethodException {
        abilities.put("Shi-jutsu", shijutsu.class.getMethod("call", Player.class));
    }

    public static boolean containsKey(String key) {
        return abilities.containsKey(key);
    }

    public static void run(String key, Player player) throws InvocationTargetException, IllegalAccessException {
        // TODO: might return success or not
        // TODO: Might have to fiddle around with args
        abilities.get(key).invoke(null, player);
    }
}
