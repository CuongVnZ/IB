package pl.nora.higherlower.utils;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import pl.nora.higherlower.HigherLower;
import pl.nora.higherlower.utils.ColorTranslate;
import pl.nora.higherlower.utils.Data;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Lose {
    private HigherLower main = HigherLower.getInstance();

    public void lose(Player player) {
        Data data = Data.getData(player);
        if (data.timeswon == 0) {
            if (Data.eco) {
                player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("LostMoney")));
                this.main.econ.withdrawPlayer((OfflinePlayer)player, (double)data.bet);
            } else {
                player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("Won").replace("%b%", String.valueOf(data.timeswon))));
            }
        } else if (Data.eco) {
            double d = this.main.getConfig().getDouble("ProcentWin") / 100.0 + 1.0;
            int n = (int)((double)data.bet * Math.pow(d, data.timeswon));
            this.main.econ.depositPlayer((OfflinePlayer)player, (double)n);
            player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("MoneyWon").replace("%b%", String.valueOf(n))));
        } else {
            player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("Won").replace("%b%", String.valueOf(data.timeswon))));
        }
        try {
            player.playSound(player.getLocation(), Sound.valueOf((String)this.main.getConfig().getString("LoseSound")), 1.0f, 1.0f);
        }
        catch (Exception exception) {
            // empty catch block
        }
        data.bet = 0;
        data.number = 0;
        data.timeswon = 0;
        player.closeInventory();
        if (!player.hasPermission(this.main.getConfig().getString("HigherLowerDelayOverridePermission"))) {
            data.delay = this.main.getConfig().getInt("Delay");
        }
    }
}

