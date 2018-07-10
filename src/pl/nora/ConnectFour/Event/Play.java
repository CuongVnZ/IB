/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package pl.nora.ConnectFour.Event;

import java.util.List;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Data;
import pl.nora.ConnectFour.Game.Game;
import pl.nora.ConnectFour.Game.GameManager;
import pl.nora.ConnectFour.Language.Language;
import pl.nora.ConnectFour.Utilities.ItemHandler;
import pl.nora.ConnectFour.Utilities.Utilities;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class Play
implements Listener {
    private ConnectFour pl;
    private Utilities util;

    public Play(ConnectFour connectFour) {
        this.pl = connectFour;
        this.util = connectFour.getUtil();
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getWhoClicked() instanceof Player) {
            final Player player = (Player)inventoryClickEvent.getWhoClicked();
            final Inventory inventory = inventoryClickEvent.getInventory();
            final Game game = this.pl.getGM().getGame(player);
            if (game == null) {
                return;
            }
            Inventory inventory2 = game.getScreen();
            if (!inventory.getName().equals(inventory2.getName())) {
                return;
            }
            inventoryClickEvent.setCancelled(true);
            ItemStack itemStack2 = inventoryClickEvent.getCurrentItem();
            if (itemStack2 == null) {
                return;
            }
            int n = inventoryClickEvent.getRawSlot();
            if (itemStack2.isSimilar(ItemHandler.quit)) {
                this.util.onClosing.add(player.getName());
                player.closeInventory();
                return;
            }
            if (itemStack2.isSimilar(ItemHandler.glass) || game.isDropping() || !game.isTurn(player) || !this.util.isLegal(n).booleanValue() || game.isOver()) {
                return;
            }
            final List<Integer> list = this.util.getRows(n, inventory);
            if (inventory2.getItem(list.get(game.getCurrentSlot()).intValue()).getDurability() != 0) {
                return;
            }
            game.setDropping(true);
            if (this.util.sounds.booleanValue()) {
                for (Player itemStack3 : game.getPlayers()) {
                    itemStack3.playSound(itemStack3.getLocation(), Sound.BLOCK_NOTE_HARP, 1.0f, 1.0f);
                }
            }
            final ItemStack itemStack = this.util.getBlockColor(player);
            new BukkitRunnable(){

                public void run() {
                    if (game.getCurrentSlot() < list.size()) {
                        ItemStack itemStack2;
                        inventory.setItem(((Integer)list.get(game.getCurrentSlot())).intValue(), itemStack);
                        if (game.getCurrentSlot() > 0) {
                            inventory.setItem(((Integer)list.get(game.getCurrentSlot() - 1)).intValue(), ItemHandler.block_unused);
                        }
                        game.setCurrentSlot(game.getCurrentSlot() + 1);
                        if (game.getCurrentSlot() >= list.size()) {
                            game.setCurrentSlot(0);
                            this.cancel();
                            game.setDropping(false);
                            Play.this.util.checkWinner(player);
                        }
                        if ((itemStack2 = inventory.getItem(((Integer)list.get(game.getCurrentSlot())).intValue())) == null || !itemStack2.isSimilar(ItemHandler.block_unused)) {
                            game.setCurrentSlot(0);
                            this.cancel();
                            game.setDropping(false);
                            Play.this.util.checkWinner(player);
                        }
                    } else {
                        game.setCurrentSlot(0);
                        this.cancel();
                        game.setDropping(false);
                        Play.this.util.checkWinner(player);
                    }
                }
            }.runTaskTimer((Plugin)ConnectFour.i(), 5, 5);
            game.switchTurn();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player)inventoryCloseEvent.getPlayer();
        final Game game = this.pl.getGM().getGame(player);
        if (game != null) {
            Player player2 = null;
            for (Player player3 : game.getPlayers()) {
                if (player3.equals((Object)player)) continue;
                player2 = player3;
            }
            if (!game.isClosing()) {
                if (this.util.onClosing.contains(player.getName())) {
                    this.util.onClosing.remove(player.getName());
                }
                game.setClosing(true);
                player2.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                player2.playSound(player2.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                if (!this.util.stopping.booleanValue()) {
                    if (!game.isOver() && this.util.stats.booleanValue()) {
                        player.sendMessage(this.pl.getLang().get("Messages.Game.Exit.Loss.Quitter"));
                        player2.sendMessage(this.pl.getLang().getRP("Messages.Game.Exit.Loss.Player", player.getName()));
                        this.pl.getData().addLoss(player);
                    } else {
                        player.sendMessage(this.pl.getLang().get("Messages.Game.Exit.NoLoss.Quitter"));
                        player2.sendMessage(this.pl.getLang().getRP("Messages.Game.Exit.NoLoss.Player", player.getName()));
                    }
                    new BukkitRunnable(){

                        public void run() {
                            Play.this.pl.getGM().removeGame(game);
                        }
                    }.runTaskLaterAsynchronously((Plugin)ConnectFour.i(), 3);
                }
            }
        }
    }

}

