package pl.nora.higherlower.listeners;

import pl.nora.higherlower.HigherLower;
import pl.nora.higherlower.utils.Data;
import pl.nora.higherlower.utils.HigherLowerGUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class OnInventoryCloseEvent
implements Listener {
    private HigherLowerGUI hlgui = HigherLowerGUI.getInstance();
    private HigherLower main = HigherLower.getInstance();

    @EventHandler
    public void onInventoryCloseEvent(final InventoryCloseEvent inventoryCloseEvent) {
        if (!(inventoryCloseEvent.getPlayer() instanceof Player)) {
            return;
        }
        Player player = (Player)inventoryCloseEvent.getPlayer();
        Data data = Data.getData(player);
        if (data.number != 0 && inventoryCloseEvent.getInventory().equals((Object)this.hlgui.higherLowerGUI)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.main, new Runnable(){

                @Override
                public void run() {
                    inventoryCloseEvent.getPlayer().openInventory(inventoryCloseEvent.getInventory());
                }
            }, 1);
        }
    }

}

