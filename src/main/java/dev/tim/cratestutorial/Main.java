package dev.tim.cratestutorial;

import dev.tim.cratestutorial.command.GiveKeyCommand;
import dev.tim.cratestutorial.command.SetCrateCommand;
import dev.tim.cratestutorial.listener.InteractListener;
import dev.tim.cratestutorial.listener.InventoryListener;
import dev.tim.cratestutorial.manager.CrateManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private CrateManager crateManager;

    @Override
    public void onEnable() {
        crateManager = new CrateManager(this);
        saveDefaultConfig();

        crateManager.setRewards();

        getCommand("givekey").setExecutor(new GiveKeyCommand());
        getCommand("setcrate").setExecutor(new SetCrateCommand(this));

        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(this), this);
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }
}
