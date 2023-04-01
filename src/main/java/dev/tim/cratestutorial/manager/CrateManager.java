package dev.tim.cratestutorial.manager;

import dev.tim.cratestutorial.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrateManager {

    private final Main plugin;

    public ItemStack[] rewards;
    public int itemIndex = 0;
    public List<Inventory> invs = new ArrayList<>();

    public CrateManager(Main plugin){
        this.plugin = plugin;
    }

    public void setRewards(){
        ItemStack[] items = new ItemStack[10];

        items[0] = new ItemStack(Material.DIRT, 32);
        items[1] = new ItemStack(Material.DIAMOND, 10);
        items[2] = new ItemStack(Material.IRON_INGOT, 64);
        items[3] = new ItemStack(Material.GOLDEN_APPLE, 10);
        items[4] = new ItemStack(Material.NETHER_STAR, 1);
        items[5] = new ItemStack(Material.OBSIDIAN, 32);
        items[6] = new ItemStack(Material.QUARTZ_BLOCK, 64);
        items[7] = new ItemStack(Material.COOKED_BEEF, 64);
        items[8] = new ItemStack(Material.ENDER_PEARL, 15);
        items[9] = new ItemStack(Material.EXPERIENCE_BOTTLE, 40);

        rewards = items;
    }

    public void openRewardsMenu(Player player){
        Inventory inv = Bukkit.createInventory(player, 54, ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Rewards");
        inv.addItem(rewards);
        player.openInventory(inv);
    }

    public void setupInventory(Inventory inv){
        int startSlot = new Random().nextInt(rewards.length);

        for(int i = 0; i < startSlot; i++){
            for(int items = 9; items < 18; items++){
                inv.setItem(items, rewards[(items + itemIndex) % rewards.length]);
            }
            itemIndex++;
        }

        ItemStack frame = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta frameMeta = frame.getItemMeta();
        frameMeta.setDisplayName(" ");
        frame.setItemMeta(frameMeta);

        for(int i : new int[]{0,1,2,3,5,6,7,8,18,19,20,21,22,23,24,25,26}){
            inv.setItem(i, frame);
        }

        ItemStack item = new ItemStack(Material.HOPPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        inv.setItem(4, item);
    }

    public void spin(Player player){
        Inventory inv = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Spinning...");
        setupInventory(inv);
        invs.add(inv);
        player.openInventory(inv);

        Random r = new Random();
        double seconds = 7.0 + (12.0 - 7.0) * r.nextDouble();

        new BukkitRunnable() {
            double delay = 0;
            int ticks = 0;
            boolean done = false;

            @Override
            public void run() {
                if(done){
                    return;
                }
                ticks++;
                delay += 1 / (20 * seconds);
                if(ticks > delay * 10){
                    ticks = 0;

                    for(int items = 9; items < 18; items++){
                        inv.setItem(items, rewards[(items + itemIndex) % rewards.length]);
                    }
                    itemIndex++;

                    if(delay >= 0.5){
                        done = true;
                        new BukkitRunnable(){
                            public void run(){
                                ItemStack item = inv.getItem(13);
                                player.getInventory().addItem(item);
                                player.updateInventory();
                                player.closeInventory();
                                cancel();
                            }
                        }.runTaskLater(Main.getPlugin(Main.class), 50);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public ItemStack[] getRewards() {
        return rewards;
    }
}
