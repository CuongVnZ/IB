/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package pl.backup.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.backup.Config;
import pl.backup.Main;
import pl.backup.managers.BackupManager;
import pl.backup.managers.UserManager;
import pl.backup.object.UserBackup;
import pl.backup.utils.Logger;
import pl.backup.utils.Util;

public class ClickListener
implements Listener {
    @EventHandler
    public void onClickc(PlayerJoinEvent playerJoinEvent) {
        UserManager.joinToGame(playerJoinEvent.getPlayer());
    }

    @EventHandler
    public void onClickCh(InventoryClickEvent inventoryClickEvent) {
        if (!Util.fixColor(Config.NameInventory).equalsIgnoreCase(inventoryClickEvent.getInventory().getName())) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        for (String string : itemMeta.getLore()) {
            if (!string.contains(Util.fixColor(Config.NameLore))) continue;
            String[] arrstring = string.split(": ");
            Player player2 = Bukkit.getPlayer((String)arrstring[1]);
            for (String string2 : itemMeta.getLore()) {
                if (!string2.contains(Util.fixColor(Config.DateLore))) continue;
                String[] arrstring2 = string2.split(": ");
                String string3 = arrstring2[1];
                ResultSet resultSet = Main.getStore().query("SELECT * FROM {P}backups WHERE uuid = '" + player2.getUniqueId() + "';");
                try {
                    while (resultSet.next()) {
                        ItemStack itemStack2;
                        ItemStack[] arritemStack;
                        int n;
                        int n2;
                        int n3;
                        UserBackup userBackup = new UserBackup(resultSet);
                        if (!Util.getDate(userBackup.getBackupTime()).contains(string3)) continue;
                        Inventory inventory = Bukkit.createInventory((InventoryHolder)player, (int)54, (String)Util.fixColor(Config.NameInventoryItems));
                        if (BackupManager.isEc(player)) {
                            n = 0;
                            arritemStack = userBackup.getEnderchestBackup();
                            n3 = arritemStack.length;
                            n2 = 0;
                            while (n2 < n3) {
                                itemStack2 = arritemStack[n2];
                                inventory.setItem(n, itemStack2);
                                ++n;
                                ++n2;
                            }
                        } else {
                            n = 3;
                            arritemStack = userBackup.getArmorBackup();
                            n3 = arritemStack.length;
                            n2 = 0;
                            while (n2 < n3) {
                                itemStack2 = arritemStack[n2];
                                inventory.setItem(n, itemStack2);
                                --n;
                                ++n2;
                            }
                            itemStack2 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
                            inventory.setItem(4, itemStack2);
                            n2 = 5;
                            ItemStack[] arritemStack2 = userBackup.getInventoryBackup();
                            int n4 = arritemStack2.length;
                            int n5 = 0;
                            while (n5 < n4) {
                                ItemStack itemStack3 = arritemStack2[n5];
                                inventory.setItem(n2, itemStack3);
                                ++n2;
                                ++n5;
                            }
                        }
                        inventory.setItem(inventory.getSize() - 1, ClickListener.itemStack(userBackup, player2));
                        player.closeInventory();
                        player.openInventory(inventory);
                    }
                }
                catch (SQLException sQLException) {
                    Logger.warning("An error occurred while loading users!", "Error: " + sQLException.getMessage());
                    sQLException.printStackTrace();
                }
            }
        }
    }

    public static ItemStack itemStack(UserBackup userBackup, Player player) {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>();
        itemMeta.setDisplayName(Util.fixColor(Config.NameItemsR));
        Iterator<String> iterator = Config.LoreItems.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            string = string.replace("{DATE}", Util.getDate(userBackup.getBackupTime()));
            string = string.replace("{NAME}", userBackup.getUser().getName());
            string = string.replace("{REASON}", userBackup.getKiller());
            arrayList.add(string);
        }
        itemMeta.setLore((List<String>)Util.fixColor(arrayList));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @EventHandler
    public void onClickRestore(InventoryClickEvent inventoryClickEvent) {
        if (!Util.fixColor(Config.NameInventoryItems).equalsIgnoreCase(inventoryClickEvent.getInventory().getName())) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        for (String string : itemMeta.getLore()) {
            if (!string.contains(Util.fixColor(Config.NameLoreItems))) continue;
            String[] arrstring = string.split(": ");
            Player player2 = Bukkit.getPlayer((String)arrstring[1]);
            for (String string2 : itemMeta.getLore()) {
                if (!string2.contains(Util.fixColor(Config.DateLoreItems))) continue;
                String[] arrstring2 = string2.split(": ");
                String string3 = arrstring2[1];
                ResultSet resultSet = Main.getStore().query("SELECT * FROM {P}backups WHERE uuid = '" + player2.getUniqueId() + "';");
                try {
                    while (resultSet.next()) {
                        UserBackup userBackup = new UserBackup(resultSet);
                        if (!Util.getDate(userBackup.getBackupTime()).contains(string3)) continue;
                        if (BackupManager.isEc(player)) {
                            BackupManager.removeEc(player);
                            player2.getEnderChest().clear();
                            player2.getEnderChest().setContents(userBackup.getEnderchestBackup());
                            player2.sendMessage(Util.fixColor(Config.RestoreEnder.replace("{P}", player.getName()).replace("{DATE}", Util.getDate(userBackup.getBackupTime()))));
                            player.closeInventory();
                            continue;
                        }
                        player2.getInventory().clear();
                        player2.getInventory().setArmorContents(userBackup.getArmorBackup());
                        player2.getInventory().setContents(userBackup.getInventoryBackup());
                        player2.sendMessage(Util.fixColor(Config.RestorePlayer.replace("{P}", player.getName()).replace("{DATE}", Util.getDate(userBackup.getBackupTime()))));
                        player.closeInventory();
                    }
                }
                catch (SQLException sQLException) {
                    Logger.warning("An error occurred while loading users!", "Error: " + sQLException.getMessage());
                    sQLException.printStackTrace();
                }
            }
        }
    }
}

