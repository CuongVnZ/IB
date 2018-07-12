package pl.nora.higherlower.utils;

import java.util.Random;

import pl.nora.higherlower.HigherLower;
import pl.nora.higherlower.utils.BetGUI;
import pl.nora.higherlower.utils.Data;
import pl.nora.higherlower.utils.HigherLowerGUI;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

@SuppressWarnings("unused")
public class GiveGame {
    private HigherLower main = HigherLower.getInstance();
    private HigherLowerGUI hlgui = HigherLowerGUI.getInstance();
    private BetGUI bg = BetGUI.getInstance();
    public boolean a = this.main.getConfig().getBoolean("Money");

    public void giveGame(Player player) {
        Data data = Data.getData(player);
        if (this.a && Data.eco) {
            data.bet = this.main.getConfig().getInt("DefaultBet");
            this.bg.prepareBetGUI(data.bet);
            player.openInventory(this.bg.betGUI);
        }
        if (!this.a && !Data.eco) {
            data.number = new Random().nextInt(this.main.getConfig().getInt("MaximumNumber") + 1);
            this.hlgui.prepareHigherLowerGUI(data.number);
        }
    }
}

