/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.SkullType
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 */
package pl.nora.ConnectFour.Invites;

import java.util.HashMap;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Invites.InvitesHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("unused")
public class InviteMenu
implements Listener {
    public static String menuName;

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onInvite(InventoryClickEvent inventoryClickEvent) {
        ItemStack itemStack;
        if (inventoryClickEvent.getInventory().getName().contains(menuName) && (itemStack = inventoryClickEvent.getCurrentItem()) != null && itemStack.getType() == Material.SKULL_ITEM) {
            Player player = (Player)inventoryClickEvent.getWhoClicked();
            SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
            if (skullMeta.getOwner() == null) {
                return;
            }
            Player player2 = Bukkit.getPlayer((String)skullMeta.getOwner());
            if (player2 != null && !player2.equals((Object)player)) {
                ConnectFour.i().getInvHandler().invite(player, player2);
                player.closeInventory();
            } else {
                player.openInventory(InviteMenu.getInviteMenu(player));
            }
        }
    }

    @SuppressWarnings("deprecation")
	public static Inventory getInviteMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, (int)54, (String)menuName);
        for (Player player2 : Bukkit.getOnlinePlayers()) {
            if (player2.equals((Object)player) || !player.canSee(player2)) continue;
            ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
            SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
            skullMeta.setOwner(player2.getName());
            itemStack.setItemMeta((ItemMeta)skullMeta);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName((Object)ChatColor.GOLD + player2.getName());
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(new ItemStack[]{itemStack});
        }
        return inventory;
    }
}

