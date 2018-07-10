/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package pl.nora.ConnectFour.Game;

import java.util.HashSet;
import java.util.List;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Game.Game;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class GameManager {
    ConnectFour pl;
    public HashSet<Game> games = new HashSet<Game>();

    public GameManager(ConnectFour connectFour) {
        this.pl = connectFour;
    }

    public Game getGame(Player player) {
        for (Game game : this.games) {
            if (!game.getPlayers().contains((Object)player)) continue;
            return game;
        }
        return null;
    }

    public void createGame(Player player, Player player2) {
        new pl.nora.ConnectFour.Game.Game(player, player2);
    }

    public void removeGame(Game game) {
        this.games.remove(game);
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public void clear() {
        this.games.clear();
    }

    public HashSet<Game> getGames() {
        return this.games;
    }
}

