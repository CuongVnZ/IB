package pl.nora.higherlower.utils;

import pl.nora.higherlower.HigherLower;
import pl.nora.higherlower.utils.Data;
import pl.nora.higherlower.utils.HigherLowerGUI;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

@SuppressWarnings("unused")
public class Win {
    private HigherLowerGUI hlgui = HigherLowerGUI.getInstance();
    private HigherLower main = HigherLower.getInstance();

    public void win(Player player) {
        Data data = Data.getData(player);
        ++data.timeswon;
        try {
            player.playSound(player.getLocation(), Sound.valueOf((String)this.main.getConfig().getString("WinSound")), 1.0f, 1.0f);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.hlgui.prepareHigherLowerGUI(data.number);
        player.openInventory(this.hlgui.higherLowerGUI);
    }
}

