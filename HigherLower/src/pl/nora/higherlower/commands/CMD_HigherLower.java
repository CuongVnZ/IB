package pl.nora.higherlower.commands;

import net.milkbowl.vault.economy.Economy;
import pl.nora.higherlower.HigherLower;
import pl.nora.higherlower.utils.ColorTranslate;
import pl.nora.higherlower.utils.Data;
import pl.nora.higherlower.utils.GiveGame;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class CMD_HigherLower
implements CommandExecutor {
    private HigherLower main = HigherLower.getInstance();

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] arrstring) {
        if (command.getName().equalsIgnoreCase("higherlower")) {
            if (arrstring.length == 0) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("NotAPlayer")));
                    return true;
                }
                if (!commandSender.hasPermission(this.main.getConfig().getString("HigherLowerOpenPermission"))) {
                    commandSender.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("NoPermissions")));
                    return true;
                }
                Player player = (Player)commandSender;
                Data data = Data.getData(player);
                if (data.delay != -1) {
                    player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("InCooldown").replace("%b%", String.valueOf(data.delay))));
                    return true;
                }
                if (Data.eco && this.main.econ.getBalance((OfflinePlayer)player) < (double)this.main.getConfig().getInt("DefaultBet")) {
                    player.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("NotEnoughBalance")));
                    return true;
                }
                new GiveGame().giveGame(player);
                return true;
            }
            if (arrstring.length == 1 && arrstring[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission(this.main.getConfig().getString("ReloadPermission"))) {
                    commandSender.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("NoPermissions")));
                    return true;
                }
                this.main.reloadConfig();
                commandSender.sendMessage(ColorTranslate.cc("&7[&a&lHigherLower&7]: The configuration has been reloaded succesfully!"));
                return true;
            }
            commandSender.sendMessage(ColorTranslate.cc(this.main.getConfig().getString("UnknownArgument")));
            return true;
        }
        return true;
    }
}

