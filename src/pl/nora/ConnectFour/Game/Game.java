package pl.nora.ConnectFour.Game;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Data;
import pl.nora.ConnectFour.Game.GameManager;
import pl.nora.ConnectFour.Game.Turn;
import pl.nora.ConnectFour.Language.Language;
import pl.nora.ConnectFour.Utilities.ItemFactory;
import pl.nora.ConnectFour.Utilities.ItemHandler;
import pl.nora.ConnectFour.Utilities.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

@SuppressWarnings("unused")
public class Game {
    private List<Player> players = new ArrayList<Player>();
    private Player player1;
    private Player player2;
    private Inventory screen;
    private int currentSlot = 0;
    private boolean isOver = false;
    private boolean isDropping = false;
    private boolean isClosing = false;
    private Turn turn = Turn.RED;
    private static int[] $SWITCH_TABLE$me$reider45$ConnectFour$Game$Turn;

    public Game(Player player, Player player2) {
        this.players.add(player);
        this.players.add(player2);
        this.player1 = player;
        this.player2 = player2;
        this.screen = Bukkit.createInventory((InventoryHolder)null, (int)54, (String)ConnectFour.i().getUtil().screenName);
        this.resetScreen();
        player.openInventory(this.screen);
        player2.openInventory(this.screen);
        ConnectFour.i().getGM().addGame(this);
    }

    public void resetScreen() {
        int n = 7;
        while (n < this.screen.getSize()) {
            this.screen.setItem(n, ItemHandler.glass);
            n += 9;
        }
        this.screen.setItem(53, ItemHandler.quit);
        this.screen.setItem(35, ItemHandler.turn_red);
        ItemStack itemStack = ItemFactory.createItem(Material.BOOK, 0, ConnectFour.i().getUtil().screenName, "§c" + this.players.get(0).getName() + "%" + "§b" + this.players.get(1).getName());
        this.screen.setItem(8, itemStack);
        int n2 = 0;
        while (n2 < this.screen.getSize()) {
            if (this.screen.getItem(n2) == null) {
                this.screen.setItem(n2, ItemHandler.block_unused);
            }
            ++n2;
        }
        this.screen.setItem(26, this.setSkull(this.player1));
    }

    public void endGame(String string) {
        this.isOver = true;
        if (ConnectFour.i().getUtil().sounds.booleanValue()) {
            this.player1.playSound(this.player1.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1.0f, 1.0f);
            this.player2.playSound(this.player2.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1.0f, 1.0f);
        }
        if (!ChatColor.stripColor((String)string).equals("Tie!")) {
            if (string.equals(this.player1.getName())) {
                ConnectFour.i().getData().addWin(this.player1, this.player2);
                ConnectFour.i().getData().addLoss(this.player2);
            } else if (string.equals(this.player2.getName())) {
                ConnectFour.i().getData().addWin(this.player2, this.player1);
                ConnectFour.i().getData().addLoss(this.player1);
            }
            ItemStack itemStack = ItemFactory.createItem(Material.STAINED_GLASS_PANE, 5, ConnectFour.i().getLang().getRP("Messages.Game.Win", string), null);
            int n = 0;
            while (n < this.screen.getSize()) {
                if (this.screen.getItem(n).getDurability() == 0 && this.screen.getItem(n).getType() != Material.DIAMOND && this.screen.getItem(n).getType() != Material.BOOK && ConnectFour.i().getUtil().isLegal(n).booleanValue()) {
                    this.screen.setItem(n, itemStack);
                }
                ++n;
            }
        } else {
            ItemStack itemStack = ItemFactory.createItem(Material.BEACON, 0, ConnectFour.i().getLang().get("Messages.Game.Tie"), null);
            this.screen.setItem(17, itemStack);
            this.screen.setItem(44, itemStack);
            this.screen.setItem(53, itemStack);
        }
    }

    public void setClosing(boolean bl) {
        this.isClosing = bl;
    }

    public void setDropping(boolean bl) {
        this.isDropping = bl;
    }

    public void setCurrentSlot(int n) {
        this.currentSlot = n;
    }

    public int getCurrentSlot() {
        return this.currentSlot;
    }

    public boolean isDropping() {
        return this.isDropping;
    }

    public boolean isClosing() {
        return this.isClosing;
    }

    public boolean isOver() {
        return this.isOver;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Inventory getScreen() {
        return this.screen;
    }

    public void switchTurn() {
        switch (Game.$SWITCH_TABLE$me$reider45$ConnectFour$Game$Turn()[this.turn.ordinal()]) {
            case 1: {
                this.turn = Turn.BLUE;
                this.screen.setItem(26, this.setSkull(this.player2));
                this.screen.setItem(35, ItemHandler.turn_blue);
                break;
            }
            case 2: {
                this.turn = Turn.RED;
                this.screen.setItem(26, this.setSkull(this.player1));
                this.screen.setItem(35, ItemHandler.turn_red);
            }
        }
    }

    @SuppressWarnings("deprecation")
	private ItemStack setSkull(Player player) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
        skullMeta.setOwner(player.getName());
        itemStack.setItemMeta((ItemMeta)skullMeta);
        return itemStack;
    }

    public boolean isTurn(Player player)
    {
        String s;
        switch((s = ConnectFour.i().getUtil().getColor(player)).hashCode())
        {
        default:
            break;

        case 82033: 
            if(s.equals("Red"))
                return turn.equals(Turn.RED);
            break;

        case 2073722: 
            if(s.equals("Blue"))
                return turn.equals(Turn.BLUE);
            break;
        }
        return false;
    }

    static int[] $SWITCH_TABLE$me$reider45$ConnectFour$Game$Turn() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$me$reider45$ConnectFour$Game$Turn;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Turn.values().length];
        try {
            arrn[Turn.BLUE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Turn.RED.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$me$reider45$ConnectFour$Game$Turn = arrn;
        return $SWITCH_TABLE$me$reider45$ConnectFour$Game$Turn;
    }
}

