package pl.nora.ConnectFour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import pl.nora.ConnectFour.CMDHandler;
import pl.nora.ConnectFour.Data;
import pl.nora.ConnectFour.Event.Play;
import pl.nora.ConnectFour.Game.Game;
import pl.nora.ConnectFour.Game.GameManager;
import pl.nora.ConnectFour.Invites.InviteMenu;
import pl.nora.ConnectFour.Invites.InvitesHandler;
import pl.nora.ConnectFour.Invites.Queues.Queue;
import pl.nora.ConnectFour.Invites.Queues.Quit;
import pl.nora.ConnectFour.Language.Language;
import pl.nora.ConnectFour.SQL.MySQL;
import pl.nora.ConnectFour.Utilities.ItemHandler;
import pl.nora.ConnectFour.Utilities.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class ConnectFour
extends JavaPlugin {
    private static ConnectFour plugin;
    private MySQL mysql;
    private Data data;
    private Utilities util;
    private InvitesHandler invHandler;
    private GameManager gameManager;
    private Language languageHandler;
    private Queue queueHandler;

    public static ConnectFour i() {
        return plugin;
    }

    public MySQL getSQL() {
        return this.mysql;
    }

    public Data getData() {
        return this.data;
    }

    public Utilities getUtil() {
        return this.util;
    }

    public InvitesHandler getInvHandler() {
        return this.invHandler;
    }

    public GameManager getGM() {
        return this.gameManager;
    }

    public Language getLang() {
        return this.languageHandler;
    }

    public Queue getQueue() {
        return this.queueHandler;
    }

    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        this.languageHandler = new Language(this);
        this.util = new Utilities(this);
        new pl.nora.ConnectFour.Utilities.ItemHandler(this);
        this.gameManager = new GameManager(this);
        this.invHandler = new InvitesHandler(this.util);
        if (this.getUtil().mysql.booleanValue()) {
            try {
                this.mysql = new MySQL(this.getConfig().getString("MySQL.Host"), this.getConfig().getString("MySQL.User"), this.getConfig().getString("MySQL.Password"), this.getConfig().getInt("MySQL.Port"), this.getConfig().getString("MySQL.DBName"));
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.data = new Data(this);
        this.queueHandler = new Queue();
        if (this.getUtil().inviteMenu.booleanValue()) {
            InviteMenu.menuName = ChatColor.translateAlternateColorCodes((char)'$', (String)this.getConfig().getString("Invites.Menu.Title"));
            Bukkit.getPluginManager().registerEvents((Listener)new InviteMenu(), (Plugin)this);
        }
        Bukkit.getPluginManager().registerEvents((Listener)new Quit(this), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Play(this), (Plugin)this);
        this.getCommand("connectfour").setExecutor((CommandExecutor)new CMDHandler(this));
    }

    public void onDisable() {
        this.getUtil().stopping = true;
        if (!this.getUtil().mysql.booleanValue()) {
            this.getData().saveFiles();
        } else {
            this.getSQL().close();
        }
        this.getLang().saveFiles();
        for (Game game : this.getGM().getGames()) {
            for (Player player : game.getPlayers()) {
                player.closeInventory();
            }
        }
        this.getGM().clear();
        this.invHandler.invites.clear();
        this.mysql = null;
        this.data = null;
        plugin = null;
    }
}

