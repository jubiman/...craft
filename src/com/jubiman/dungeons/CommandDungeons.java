package com.jubiman.dungeons;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
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

                // Item meta stuff
                ItemMeta im = sword.getItemMeta();

                im.setDisplayName("§dOnimaru Kunitsuna");
                String[] lore = new String[]{
                        "§7Damage: §c+350",
                        "",
                        "§6Ability: 死術 (Shi-jutsu): §e§lRIGHT CLICK§r",
                        "§7Teleport behind the closest §b5 §7enemies in a",
                        "§615 §eblock radius to kill them.",
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
                sword.setItemMeta(im);

                // Set NBT tags
                net.minecraft.server.v1_8_R3.ItemStack nmsSword = CraftItemStack.asNMSCopy(sword);
                NBTTagCompound swordCompound = nmsSword.hasTag() ? nmsSword.getTag() : new NBTTagCompound();

                // Add modifiers
                NBTTagList modifiers = new NBTTagList();
                NBTTagCompound damage = new NBTTagCompound();
                damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
                damage.set("Name", new NBTTagString("generic.attackDamage"));
                damage.set("Amount", new NBTTagInt(350));
                NBTTagCompound attackSpeed = new NBTTagCompound();
                attackSpeed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
                attackSpeed.set("Name", new NBTTagString("generic.attackSpeed"));
                attackSpeed.set("Amount", new NBTTagInt(20));

                // Set the modifiers
                modifiers.add(damage);
                modifiers.add(attackSpeed);
                swordCompound.set("AttributeModifiers", modifiers);

                // Add enchantments
                NBTTagList enchantments = new NBTTagList();
                NBTTagCompound boa = new NBTTagCompound();
                boa.set("id", new NBTTagString("DAMAGE_ARTHROPODS"));
                boa.set("lvl", new NBTTagInt(2147483647));
                NBTTagCompound sharp = new NBTTagCompound();
                sharp.set("id", new NBTTagString("DAMAGE_ALL"));
                sharp.set("lvl", new NBTTagInt(2147483647));
                NBTTagCompound smite = new NBTTagCompound();
                smite.set("id", new NBTTagString("DAMAGE_UNDEAD"));
                smite.set("lvl", new NBTTagInt(2147483647));
                NBTTagCompound fa = new NBTTagCompound();
                fa.set("id", new NBTTagString("FIRE_ASPECT"));
                fa.set("lvl", new NBTTagInt(5));
                NBTTagCompound loot = new NBTTagCompound();
                loot.set("id", new NBTTagString("LOOT_BONUS_MOBS"));
                loot.set("lvl", new NBTTagInt(5));

                // Set the enchantments
                enchantments.add(boa);
                enchantments.add(sharp);
                enchantments.add(smite);
                enchantments.add(fa);
                enchantments.add(loot);
                swordCompound.set("ench", enchantments);

                // Additional tags
                swordCompound.set("Unbreakable", new NBTTagByte((byte) 1));
                swordCompound.set("Ability", new NBTTagString("Shin-jutsu"));

                // Adding the item
                nmsSword.setTag(swordCompound);
                sword = CraftItemStack.asBukkitCopy(nmsSword);
                player.getInventory().addItem(sword);

                return true;
            }
        }
        return false;
    }
}
