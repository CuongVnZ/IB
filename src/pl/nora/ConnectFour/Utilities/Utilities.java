package pl.nora.ConnectFour.Utilities;

import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Game.Game;
import pl.nora.ConnectFour.Game.GameManager;
import pl.nora.ConnectFour.Language.Language;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class Utilities {
    public ConnectFour plugin;
    public HashSet<String> commandList;
    public Boolean stats;
    public Boolean sounds;
    public Boolean commands;
    public Boolean inviteMenu;
    public Boolean stopping;
    public Boolean queueEnabled;
    public String screenName;
    public Boolean mysql;
    public List<String> onClosing = new ArrayList<String>();
    public String prefix = "\ufffdc[Connect Four] \ufffdr";

    public Utilities(ConnectFour connectFour) {
        this.plugin = connectFour;
        this.stopping = false;
        this.stats = connectFour.getConfig().getBoolean("Game.Stats");
        this.sounds = connectFour.getConfig().getBoolean("Game.Sounds");
        this.screenName = ChatColor.translateAlternateColorCodes((char)'$', (String)connectFour.getConfig().getString("Game.Screen.Title"));
        this.commands = connectFour.getConfig().getBoolean("Game.Commands.Enabled");
        this.commandList = new HashSet<String>();
        this.mysql = connectFour.getConfig().getBoolean("MySQL.Enabled");
        this.inviteMenu = connectFour.getConfig().getBoolean("Invites.Menu.Enabled");
        this.queueEnabled = connectFour.getConfig().getBoolean("Invites.Queue.Enabled");
        if (this.commands.booleanValue()) {
            for (String string : connectFour.getConfig().getStringList("Game.Commands.Execute")) {
                this.commandList.add(string);
            }
        }
    }

    public void helpMenu(Player player) {
        player.sendMessage(this.plugin.getLang().get("Messages.Menu"));
    }

    public String getColor(Player player) {
        Game game = this.plugin.getGM().getGame(player);
        if (game == null) {
            return "";
        }
        if (game.getPlayers().get(0).equals((Object)player)) {
            return "Red";
        }
        return "Blue";
    }

    public ItemStack getBlockColor(Player player)
    {
        String s;
        switch((s = getColor(player)).hashCode())
        {
        default:
            break;

        case 82033: 
            if(s.equals("Red"))
                return ItemHandler.block_red;
            break;

        case 2073722: 
            if(s.equals("Blue"))
                return ItemHandler.block_blue;
            break;
        }
        return null;
    }

    public List<Integer> getRows(Integer n, Inventory inventory) {
        int n2;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n3 = n2 = n % 9;
        while (n3 < inventory.getSize()) {
            arrayList.add(n3);
            n3 += 9;
        }
        return arrayList;
    }

    public Boolean isLegal(Integer n) {
        if (n != 8 && n != 17 && n != 26 && n != 35 && n != 44 && n != 53) {
            return true;
        }
        return false;
    }

    public Boolean noMovesLeft(Game game) {
        if (game == null) {
            return false;
        }
        int n = 0;
        while (n < game.getScreen().getSize()) {
            if (game.getScreen().getItem(n) != null && this.isLegal(n).booleanValue() && game.getScreen().getItem(n).getDurability() == 0) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public void checkWinner(final Player player) {
        new BukkitRunnable(){

            public void run() {
                Game game = Utilities.this.plugin.getGM().getGame(player);
                if (game == null) {
                    return;
                }
                if (!game.isOver()) {
                    if (Utilities.this.hasWon(Utilities.this.getColor(player), player).booleanValue()) {
                        for (Player player2 : game.getPlayers()) {
                            player2.sendMessage(Utilities.this.plugin.getLang().getRP("Messages.Game.Win", player.getName()));
                        }
                        game.endGame(player.getName());
                        this.cancel();
                    } else if (Utilities.this.noMovesLeft(game).booleanValue()) {
                        for (Player player3 : game.getPlayers()) {
                            player3.playSound(player3.getLocation(), Sound.ENTITY_CAT_PURREOW, 2.0f, 2.0f);
                            player3.sendMessage(Utilities.this.plugin.getLang().get("Messages.Game.Tie"));
                        }
                        game.endGame("\ufffde\ufffdlTie!");
                        this.cancel();
                    }
                }
            }
        }.runTaskLater((Plugin)this.plugin, 5);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Boolean hasWon(String s, Player player)
    {
        if(plugin.getGM().getGame(player) == null)
            return Boolean.valueOf(false);
        Game game = plugin.getGM().getGame(player);
        Inventory inventory = game.getScreen();
        ItemStack itemstack = null;
        String s1;
        switch((s1 = s).hashCode())
        {
        default:
            break;

        case 82033: 
            if(s1.equals("Red"))
                itemstack = ItemHandler.block_red;
            break;

        case 2073722: 
            if(s1.equals("Blue"))
                itemstack = ItemHandler.block_blue;
            break;
        }
        ArrayList arraylist = new ArrayList();
        int i = 0;
        for(int k = 0; k < inventory.getSize(); k++)
            if(inventory.getItem(k) != null)
            {
                if(inventory.getItem(k).isSimilar(itemstack))
                {
                    arraylist.add(Integer.valueOf(k));
                    i++;
                } else
                {
                    arraylist.clear();
                    i = 0;
                }
                if(i == 4)
                {
                    int i1;
                    for(Iterator iterator = arraylist.iterator(); iterator.hasNext(); inventory.setItem(i1, ItemHandler.diamond))
                        i1 = ((Integer)iterator.next()).intValue();

                    return Boolean.valueOf(true);
                }
            }

        for(int l = 0; l < inventory.getSize(); l++)
        {
            int j = 0;
            for(int j1 = l; j1 < inventory.getSize(); j1 += 9)
                if(inventory.getItem(j1) != null)
                {
                    if(inventory.getItem(j1).isSimilar(itemstack))
                    {
                        arraylist.add(Integer.valueOf(j1));
                        j++;
                    } else
                    {
                        arraylist.clear();
                        j = 0;
                    }
                    if(j == 4)
                    {
                        int i2;
                        for(Iterator iterator1 = arraylist.iterator(); iterator1.hasNext(); inventory.setItem(i2, ItemHandler.diamond))
                            i2 = ((Integer)iterator1.next()).intValue();

                        return Boolean.valueOf(true);
                    }
                }

            j = 0;
            for(int k1 = l; k1 < inventory.getSize(); k1 += 10)
                if(inventory.getItem(k1) != null)
                {
                    if(inventory.getItem(k1).isSimilar(itemstack))
                    {
                        arraylist.add(Integer.valueOf(k1));
                        j++;
                    } else
                    {
                        arraylist.clear();
                        j = 0;
                    }
                    if(j == 4)
                    {
                        int j2;
                        for(Iterator iterator2 = arraylist.iterator(); iterator2.hasNext(); inventory.setItem(j2, ItemHandler.diamond))
                            j2 = ((Integer)iterator2.next()).intValue();

                        return Boolean.valueOf(true);
                    }
                }

            j = 0;
            for(int l1 = l; l1 < inventory.getSize(); l1 += 8)
                if(inventory.getItem(l1) != null)
                {
                    if(inventory.getItem(l1).isSimilar(itemstack))
                    {
                        arraylist.add(Integer.valueOf(l1));
                        j++;
                    } else
                    {
                        arraylist.clear();
                        j = 0;
                    }
                    if(j == 4)
                    {
                        int k2;
                        for(Iterator iterator3 = arraylist.iterator(); iterator3.hasNext(); inventory.setItem(k2, ItemHandler.diamond))
                            k2 = ((Integer)iterator3.next()).intValue();

                        return Boolean.valueOf(true);
                    }
                }

        }

        return Boolean.valueOf(false);
    }

}

