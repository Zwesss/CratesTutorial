package dev.tim.cratestutorial.listener;

import dev.tim.cratestutorial.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private Main plugin;

    public InventoryListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Rewards")){
            e.setCancelled(true);
        }

        if(plugin.getCrateManager().invs.contains(e.getInventory())){
            e.setCancelled(true);
        }

    }
}
