package com.jubiman.dungeons.commands;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandDungeons implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0 && Objects.equals(args[0], "give")) {
                // TODO: Placeholder
                Player player = (Player) sender;

                // Create the katana (iron sword)
                ItemStack sword = new ItemStack(Material.IRON_SWORD);

                // Set NBT tags
                net.minecraft.server.v1_8_R3.ItemStack nmsSword = CraftItemStack.asNMSCopy(sword);
                NBTTagCompound swordCompound = nmsSword.hasTag() ? nmsSword.getTag() : new NBTTagCompound();

                // Add modifiers
                // TODO: add custom modifiers instead of minecraft ones (like how Hypixel (supposedly) does it) with custom dmg calc
                NBTTagList modifiers = new NBTTagList();
                NBTTagCompound damage = new NBTTagCompound();
                damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
                damage.set("Name", new NBTTagString("generic.attackDamage"));
                damage.set("Amount", new NBTTagInt(350));
                damage.set("UUIDMost", new NBTTagInt(99526));
                damage.set("UUIDLeast", new NBTTagInt(144497));
                damage.set("Operation", new NBTTagInt(0));

                // Set the modifiers
                modifiers.add(damage);
                swordCompound.set("AttributeModifiers", modifiers);

                // Additional tags
                swordCompound.set("Unbreakable", new NBTTagByte((byte) 1));
                swordCompound.set("Ability", new NBTTagString("Shi-jutsu"));
                swordCompound.set("AbilityKey", new NBTTagString("RMB"));

                // Update the sword so we can use ItemMeta
                nmsSword.setTag(swordCompound);
                sword = CraftItemStack.asBukkitCopy(nmsSword);


                // Item meta stuff
                ItemMeta im = sword.getItemMeta();

                // Add enchantments
                im.addEnchant(Enchantment.DAMAGE_ALL, 32767, true);
                im.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 32767, true);
                im.addEnchant(Enchantment.DAMAGE_UNDEAD, 32767, true);
                im.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
                im.addEnchant(Enchantment.LOOT_BONUS_MOBS, 5, true);

                im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                im.setDisplayName("§dOnimaru Kunitsuna");


                /* StringBuilder enchantmentsString = new StringBuilder();
                for (Enchantment ench : im.getEnchants().keySet()) {
                    enchantmentsString.append(ench.getName()).append(im.getEnchantLevel(ench)).append(", ");
                }
                enchantmentsString.delete(enchantmentsString.length()-3, enchantmentsString.length()-1);*/

                String[] lore = new String[]{
                        "§7Damage: §c" + swordCompound.getList("AttributeModifiers", 10).get(0).getInt("Amount"),
                        // TODO: Add custom enchantments string + newline manipulator
                        //"",
                        //enchantmentsString.toString(),
                        "",
                        "§6Ability: 死術 (Shi-jutsu) §e§lRIGHT CLICK§r",
                        "§7Teleport behind the closest §b5 §7enemies in a",
                        "§e15 §7block radius to kill them.",
                        "§8Cooldown: §a7s",
                        "",
                        "§4Omae wa Mou Shinde iru!",
                        "§4お前はもう死んでいる！",
                        "§5As one of the Five Swords under Heaven (Tenkagoken)}",
                        "§5this katana excels in killing demons, especially the Oni.",
                        "",
                        "§d§lMYTHIC SWORD§r"
                };
                im.setLore(Arrays.asList(lore));

                // Adding the item
                sword.setItemMeta(im);
                player.getInventory().addItem(sword);

                return true;
            }
        }
        return false;
    }
}
