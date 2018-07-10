/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package pl.nora.ConnectFour.Invites.Queues;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Invites.Queues.Queue;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("unused")
public class Quit
implements Listener {
    private ConnectFour plugin;

    public Quit(ConnectFour connectFour) {
        this.plugin = connectFour;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        if (this.plugin.getQueue().isInQueue(playerQuitEvent.getPlayer())) {
            this.plugin.getQueue().remove(playerQuitEvent.getPlayer());
        }
    }
}

