package pl.nora.higherlower.listeners;

import java.util.Random;

import net.milkbowl.vault.economy.Economy;
import pl.nora.higherlower.HigherLower;
import pl.nora.higherlower.utils.BetGUI;
import pl.nora.higherlower.utils.ColorTranslate;
import pl.nora.higherlower.utils.Data;
import pl.nora.higherlower.utils.GiveGame;
import pl.nora.higherlower.utils.HigherLowerGUI;
import pl.nora.higherlower.utils.Lose;
import pl.nora.higherlower.utils.Win;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
public class OnInventoryClickEvent
implements Listener {
    private BetGUI bg = BetGUI.getInstance();
    private HigherLower main = HigherLower.getInstance();
    private HigherLowerGUI hlgui = HigherLowerGUI.getInstance();

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getCurrentItem() != null) {
            Player player = null;
            if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
                return;
            }
            player = (Player)inventoryClickEvent.getWhoClicked();
            Data data = Data.getData(player);
            if (inventoryClickEvent.getCurrentItem().hasItemMeta() && inventoryClickEvent.getCurrentItem().getItemMeta().hasDisplayName()) {
                String string = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
                if (new GiveGame().a && inventoryClickEvent.getClickedInventory().equals((Object)this.bg.betGUI)) {
                    inventoryClickEvent.setCancelled(true);
                    if (string.equals(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.1.Name")))) {
                        data.bet += this.main.getConfig().getInt("BetTiles.1.Value");
                    }
                    if (string.equals(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.2.Name")))) {
                        data.bet += this.main.getConfig().getInt("BetTiles.2.Value");
                    }
                    if (string.equals(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.3.Name")))) {
                        data.bet += this.main.getConfig().getInt("BetTiles.3.Value");
                    }
                    if (string.equals(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.4.Name")))) {
                        data.bet += this.main.getConfig().getInt("BetTiles.4.Value");
                    }
                    if (string.equals(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.5.Name")))) {
                        data.bet += this.main.getConfig().getInt("BetTiles.5.Value");
                    }
                    if (string.equals(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.6.Name")))) {
                        data.bet += this.main.getConfig().getInt("BetTiles.6.Value");
                    }
                    if (string.equals(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.fill.Name")))) {
                        data.number = new Random().nextInt(this.main.getConfig().getInt("MaximumNumber")) + 1;
                        this.hlgui.prepareHigherLowerGUI(data.number);
                        player.openInventory(this.hlgui.higherLowerGUI);
                        return;
                    }
                    if (this.main.econ.getBalance((OfflinePlayer)player) < (double)data.bet) {
                        player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("CantGoHigher")));
                        data.bet = (int)this.main.econ.getBalance((OfflinePlayer)player);
                    }
                    if (data.bet > this.main.getConfig().getInt("MaximumBet")) {
                        player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("CantGoHigher")));
                        data.bet = this.main.getConfig().getInt("MaximumBet");
                    }
                    if (data.bet < this.main.getConfig().getInt("MinimumBet")) {
                        player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("CantGoLower")));
                        data.bet = this.main.getConfig().getInt("MinimumBet");
                    }
                    try {
                        player.playSound(player.getLocation(), Sound.valueOf((String)this.main.getConfig().getString("BetSound")), 1.0f, 1.0f);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.bg.prepareBetGUI(data.bet);
                    player.openInventory(this.bg.betGUI);
                }
                if (inventoryClickEvent.getClickedInventory().equals((Object)this.hlgui.higherLowerGUI)) {
                    inventoryClickEvent.setCancelled(true);
                    if (string.equalsIgnoreCase(ColorTranslate.cc(this.main.getConfig().getString("HigherLowerTiles.higher.Name")))) {
                        int n = data.number;
                        data.number = new Random().nextInt(this.main.getConfig().getInt("MaximumNumber")) + 1;
                        if (data.number >= n) {
                            new Win().win(player);
                        } else {
                            new Lose().lose(player);
                        }
                    }
                    if (string.equalsIgnoreCase(ColorTranslate.cc(this.main.getConfig().getString("HigherLowerTiles.lower.Name")))) {
                        int n = data.number;
                        data.number = new Random().nextInt(this.main.getConfig().getInt("MaximumNumber")) + 1;
                        if (data.number <= n) {
                            new Win().win(player);
                        } else {
                            new Lose().lose(player);
                        }
                    }
                }
            }
        }
    }
}

