package pl.nora.higherlower.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

import pl.nora.higherlower.HigherLower;

public class Data {
    public static HashMap<Player, Data> datas = new HashMap<Player, Data>();
    public static boolean eco = HigherLower.main.setupEconomy();
    public int bet = 0;
    public int timeswon = 0;
    public int number = 0;
    public int delay = -1;

    public static Data getData(Player player) {
        if (player.isOnline() && !datas.containsKey((Object)player)) {
            datas.put(player, new Data());
        }
        return datas.get((Object)player);
    }
}

