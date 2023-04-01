package dev.tim.cratestutorial.command;

import dev.tim.cratestutorial.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCrateCommand implements CommandExecutor {


    private Main plugin;

    public SetCrateCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Location playerLocation = player.getLocation();

            if(player.hasPermission("crate.set")){
                player.getWorld().getBlockAt(playerLocation).setType(Material.CHEST);
                plugin.getConfig().set("crate", playerLocation);
                plugin.saveConfig();
            } else {
                player.sendMessage(ChatColor.RED + "Je hebt geen permissie voor dit command!");
            }
        }
        return true;
    }
}
