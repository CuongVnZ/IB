/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package pl.nora.ConnectFour.Invites;

import java.util.ArrayList;
import java.util.HashMap;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Game.Game;
import pl.nora.ConnectFour.Game.GameManager;
import pl.nora.ConnectFour.Invites.Queues.Queue;
import pl.nora.ConnectFour.Language.Language;
import pl.nora.ConnectFour.Utilities.Utilities;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class InvitesHandler {
    public HashMap<Player, ArrayList<Player>> invites;
    Utilities util;

    public InvitesHandler(Utilities utilities) {
        this.util = utilities;
        this.invites = new HashMap<Player, ArrayList<Player>>();
    }

    public void invite(final Player player, final Player player2) {
        if (player2 == null) {
            player.sendMessage(this.util.plugin.getLang().get("Messages.Invites.Error.NotFound"));
            return;
        }
        if (player2.getName().equals(player.getName())) {
            player.sendMessage(this.util.plugin.getLang().get("Messages.Invites.Error.SelfInvite"));
            return;
        }
        if (this.invites.containsKey((Object)player2) && this.invites.get((Object)player2).contains((Object)player)) {
            player.sendMessage(this.util.plugin.getLang().getRP("Messages.Invites.Error.AlreadyInvited", player2.getName()));
            return;
        }
        if (ConnectFour.i().getGM().getGame(player2) != null) {
            player.sendMessage(this.util.plugin.getLang().getRP("Messages.Invites.Error.InGame", player2.getName()));
            return;
        }
        ArrayList<Player> arrayList = new ArrayList<Player>();
        if (!this.invites.containsKey((Object)player2)) {
            this.invites.put(player2, arrayList);
        }
        arrayList = this.invites.get((Object)player2);
        arrayList.add(player);
        this.invites.put(player2, arrayList);
        player.sendMessage(" ");
        player2.sendMessage(" ");
        player.sendMessage(this.util.plugin.getLang().getRP("Messages.Invites.Success", player2.getName()));
        if (this.util.sounds.booleanValue()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 1.0f);
            player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 1.0f);
        }
        player2.sendMessage(this.util.plugin.getLang().getRP("Messages.Invites.Receive", player.getName()).replace("%TIME%", "" + this.util.plugin.getConfig().getInt("Invites.Time")));
        player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_PLING, 4.0f, 4.0f);
        new BukkitRunnable(){

            public void run() {
                if (InvitesHandler.this.invites.containsKey((Object)player2) && InvitesHandler.this.invites.get((Object)player2).contains((Object)player)) {
                    InvitesHandler.this.invites.remove((Object)player2);
                    if (ConnectFour.i().getGM().getGame(player) == null) {
                        player.sendMessage(InvitesHandler.this.util.plugin.getLang().getRP("Messages.Invites.Error.Expired.Player", player2.getName()));
                        player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BASS, 1.0f, 1.0f);
                    }
                    if (ConnectFour.i().getGM().getGame(player2) == null) {
                        player2.sendMessage(InvitesHandler.this.util.plugin.getLang().getRP("Messages.Invites.Error.Expired.Invited", player.getName()));
                        player2.playSound(player2.getLocation(), Sound.BLOCK_NOTE_BASS, 1.0f, 1.0f);
                    }
                }
            }
        }.runTaskLaterAsynchronously((Plugin)ConnectFour.i(), 20 * (long)this.util.plugin.getConfig().getInt("Invites.Time"));
    }

    public void accept(Player player, Player player2) {
        if (player2 == null) {
            player.sendMessage(this.util.plugin.getLang().get("Messages.Invites.Error.NotFound"));
            return;
        }
        if (ConnectFour.i().getGM().getGame(player2) != null) {
            player.sendMessage(this.util.plugin.getLang().getRP("Messages.Invites.Error.InGame", player2.getName()));
            return;
        }
        if (!this.invites.containsKey((Object)player)) {
            player.sendMessage(this.util.plugin.getLang().getRP("Messages.Invites.Tip", player2.getName()));
            return;
        }
        if (!this.invites.get((Object)player).contains((Object)player2)) {
            player.sendMessage(this.util.plugin.getLang().getRP("Messages.Invites.Tip", player2.getName()));
            return;
        }
        if (this.util.plugin.getQueue().isInQueue(player)) {
            this.util.plugin.getQueue().remove(player);
        }
        if (this.util.plugin.getQueue().isInQueue(player2)) {
            this.util.plugin.getQueue().remove(player2);
        }
        this.invites.get((Object)player).clear();
        this.invites.remove((Object)player);
        ConnectFour.i().getGM().createGame(player2, player);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }

}

