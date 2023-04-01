package dev.tim.cratestutorial.listener;

import dev.tim.cratestutorial.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InteractListener implements Listener {

    private final Main plugin;

    public InteractListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta keyMeta = key.getItemMeta();
        keyMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Crate Key");
        keyMeta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Rechtermuisknop om de crate", ChatColor.GRAY + "te openen"));
        keyMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        keyMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        key.setItemMeta(keyMeta);

        if (e.getClickedBlock() != null) {
            if (plugin.getConfig().get("crate") != null) {
                Block crate = player.getWorld().getBlockAt((Location) plugin.getConfig().get("crate"));
                if (e.getClickedBlock().equals(crate)) {
                    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (player.getInventory().getItemInMainHand().equals(key)) {
                            player.getInventory().removeItem(key);
                            plugin.getCrateManager().spin(player);
                        } else {
                            player.sendMessage(ChatColor.RED + "Je moet een crate key in je hand hebben!");
                        }
                        e.setCancelled(true);
                    } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                        plugin.getCrateManager().openRewardsMenu(player);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
