/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package pl.nora.ConnectFour.Utilities;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Language.Language;
import pl.nora.ConnectFour.Utilities.ItemFactory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class ItemHandler {
    private ConnectFour pl;
    public static ItemStack glass;
    public static ItemStack block_unused;
    public static ItemStack block_red;
    public static ItemStack block_blue;
    public static ItemStack quit;
    public static ItemStack turn_red;
    public static ItemStack turn_blue;
    public static ItemStack diamond;
    public static ItemStack nextPage;

    public ItemHandler(ConnectFour connectFour) {
        this.pl = connectFour;
        this.registerItems();
    }

    public void registerItems() {
        glass = ItemFactory.createItem(Material.STAINED_GLASS_PANE, 15, "-", null);
        block_unused = ItemFactory.createItem(Material.STAINED_GLASS_PANE, 0, this.pl.getLang().get("Messages.Items.EmptyPane"), null);
        block_red = ItemFactory.createItem(Material.STAINED_GLASS_PANE, 14, this.pl.getLang().get("Messages.Items.Red.Pane"), null);
        block_blue = ItemFactory.createItem(Material.STAINED_GLASS_PANE, 11, this.pl.getLang().get("Messages.Items.Blue.Pane"), null);
        turn_red = ItemFactory.createItem(Material.REDSTONE_BLOCK, -1, this.pl.getLang().get("Messages.Items.Red.Turn"), null);
        turn_blue = ItemFactory.createItem(Material.LAPIS_BLOCK, -1, this.pl.getLang().get("Messages.Items.Blue.Turn"), null);
        diamond = ItemFactory.createItem(Material.DIAMOND, -1, this.pl.getLang().get("Messages.Items.WinningMove"), null);
        quit = ItemFactory.createItem(Material.NETHER_STAR, -1, this.pl.getLang().get("Messages.Items.Exit.Title"), this.pl.getLang().get("Messages.Items.Exit.Description"));
        nextPage = ItemFactory.createItem(Material.SKULL_ITEM, -1, "reider45", "next page");
    }
}

