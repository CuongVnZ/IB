/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package pl.nora.ConnectFour.Invites.Queues;

import java.util.ArrayList;
import java.util.List;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Game.GameManager;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Queue {
    private static List<Player> queue = new ArrayList<Player>();

    public int getSize() {
        return queue.size();
    }

    public void attemptMatchMake() {
        if (queue.size() >= 2) {
            Player player = this.getNext();
            this.remove(player);
            Player player2 = this.getNext();
            this.remove(player2);
            ConnectFour.i().getGM().createGame(player, player2);
        }
    }

    public void addPlayer(Player player) {
        queue.add(player);
        this.attemptMatchMake();
    }

    public boolean isEmpty() {
        if (queue.size() == 0) {
            return true;
        }
        return false;
    }

    public boolean isInQueue(Player player) {
        return queue.contains((Object)player);
    }

    public void remove(Player player) {
        queue.remove((Object)player);
    }

    public Player getNext() {
        if (this.isEmpty()) {
            return null;
        }
        return queue.get(0);
    }
}

