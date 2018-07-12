package pl.nora.higherlower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import net.milkbowl.vault.economy.Economy;
import pl.nora.higherlower.commands.CMD_HigherLower;
import pl.nora.higherlower.listeners.OnInventoryClickEvent;
import pl.nora.higherlower.listeners.OnInventoryCloseEvent;
import pl.nora.higherlower.utils.Data;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class HigherLower
extends JavaPlugin {
    public static HigherLower main;
    public Economy econ = null;
    Plugin pl = Bukkit.getPluginManager().getPlugin("Vault");

    public static HigherLower getInstance() {
        return main;
    }

    public void onEnable() {
        main = this;
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.getCommand("higherlower").setExecutor((CommandExecutor)new CMD_HigherLower());
        Bukkit.getPluginManager().registerEvents((Listener)new OnInventoryClickEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new OnInventoryCloseEvent(), (Plugin)this);
    }

    public boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> registeredServiceProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (registeredServiceProvider == null) {
                return false;
            }
            this.econ = (Economy)registeredServiceProvider.getProvider();
            return true;
        }
        return false;
    }

    public void runnableRunner() {
        new BukkitRunnable(){

            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Data data = Data.getData(player);
                    if (data.delay == -1) continue;
                    --data.delay;
                }
            }
        }.runTaskTimer((Plugin)this, 0, 20);
    }

}

