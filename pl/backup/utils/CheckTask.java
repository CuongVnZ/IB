/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package pl.backup.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.backup.Config;
import pl.backup.managers.UserManager;
import pl.backup.object.User;
import pl.backup.utils.Util;

@SuppressWarnings("unused")
public class CheckTask
extends BukkitRunnable {
    public void run() {
        for (Object player : Util.getOnlinePlayers()) {
            User user = UserManager.getUser(player);
            user.createBackup("Backup Time");
            Util.sendMsg((CommandSender)player, Config.CreateTaskMessage);
        }
    }
}

