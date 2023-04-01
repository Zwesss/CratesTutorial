package dev.tim.cratestutorial.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GiveKeyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("crates.givekey")){
                if(args.length == 1){
                    if(Bukkit.getPlayer(args[0]) != null){
                        Player target = Bukkit.getPlayer(args[0]);

                        ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta keyMeta = key.getItemMeta();
                        keyMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Crate Key");
                        keyMeta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Rechtermuisknop om de crate", ChatColor.GRAY + "te openen"));
                        keyMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                        keyMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        key.setItemMeta(keyMeta);

                        target.getInventory().addItem(key);
                        target.sendMessage(ChatColor.GREEN + "Je hebt een crate key gekregen!");
                        player.sendMessage(ChatColor.GREEN + "Crate key gegeven aan " + target.getName());
                    } else {
                        player.sendMessage(ChatColor.RED + "Speler niet gevonden!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Gebruik: /givekey [speler]");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Je hebt geen permissie voor dit command!");
            }
        }
        return true;
    }
}
