/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 */
package pl.backup.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import pl.backup.Config;
import pl.backup.Main;
import pl.backup.api.PlayerCommand;
import pl.backup.managers.BackupManager;
import pl.backup.managers.UserManager;
import pl.backup.object.User;
import pl.backup.object.UserBackup;
import pl.backup.utils.ItemBuilder;
import pl.backup.utils.Logger;
import pl.backup.utils.Util;

@SuppressWarnings("unused")
public class BackupCommand
extends PlayerCommand {
    public BackupCommand() {
        super(Config.CommandName, "Player backups", Config.CommandsUsage, Config.CommandPermission, Config.CommandAliases);
    }

    @Override
    public boolean onCommand(Player player, String[] arrstring) {
        if (arrstring.length != 2) {
            return Util.sendMsg((CommandSender)player, Config.CommandsUsage);
        }
        if (BackupManager.isEc(player)) {
            BackupManager.removeEc(player);
        }
        if (arrstring[0].equalsIgnoreCase("create") || arrstring[0].equalsIgnoreCase("c")) {
            if (arrstring[1].equalsIgnoreCase("all") && arrstring.length == 2) {
                for (Object player2 : Util.getOnlinePlayers()) {
                    Util.sendMsg((CommandSender)player2, Config.CommandsAll);
                    User user = UserManager.getUser(player2);
                    user.createBackup(Config.CommandsCreateby.replace("{NAME}", player.getName()));
                }
            } else if (!arrstring[1].equalsIgnoreCase("all")) {
                Player player3 = Bukkit.getPlayer((String)arrstring[1]);
                if (player3 == null) {
                    return Util.sendMsg((CommandSender)player, Config.CommandsPlayerIsOffline);
                }
                User user = UserManager.getUser(player3);
                user.createBackup(Config.CommandsCreateby.replace("{NAME}", player.getName()));
                return Util.sendMsg((CommandSender)player, Config.CommandsPlayer);
            }
            return true;
        }
        if (arrstring[0].equalsIgnoreCase("player") || arrstring[0].equalsIgnoreCase("pl")) {
            Player player4 = Bukkit.getPlayer((String)arrstring[1]);
            if (player4 == null) {
                return Util.sendMsg((CommandSender)player, Config.CommandsPlayerIsOffline);
            }
            BackupCommand.setup(player4, player);
            return true;
        }
        if (arrstring[0].equalsIgnoreCase("enderchest") || arrstring[0].equalsIgnoreCase("ec")) {
            Player player5 = Bukkit.getPlayer((String)arrstring[1]);
            if (player5 == null) {
                return Util.sendMsg((CommandSender)player, Config.CommandsPlayerIsOffline);
            }
            BackupManager.addEc(player, player5);
            BackupCommand.setup(player5, player);
            return true;
        }
        return true;
    }

    public static void setup(Player player, Player player2) {
        String string = Config.NameInventory;
        Inventory inventory = Bukkit.createInventory((InventoryHolder)player, (int)54, (String)Util.fixColor(string));
        ResultSet resultSet = Main.getStore().query("SELECT * FROM {P}backups WHERE UUID = '" + player.getUniqueId() + "';");
        try {
            while (resultSet.next()) {
                UserBackup userBackup = new UserBackup(resultSet);
                inventory.addItem(new ItemStack[]{ItemBuilder.itemStack(userBackup, player)});
            }
            player2.openInventory(inventory);
        }
        catch (SQLException sQLException) {
            Logger.warning("An error occurred while loading users!", "Error: " + sQLException.getMessage());
            sQLException.printStackTrace();
        }
    }
}

