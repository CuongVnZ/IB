package pl.nora.higherlower.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.nora.higherlower.HigherLower;
import pl.nora.higherlower.utils.ColorTranslate;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
public class BetGUI {
    private HigherLower main = HigherLower.getInstance();
    public Inventory betGUI;
    public static BetGUI bg = new BetGUI();

    public static BetGUI getInstance() {
        return bg;
    }

    public void prepareBetGUI(int n) {
        this.betGUI = Bukkit.createInventory((InventoryHolder)null, (int)27, (String)ColorTranslate.cc(this.main.getConfig().getString("BetGUITitle")));
        ItemStack itemStack = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.1.Item")), this.main.getConfig().getInt("BetTiles.1.Amount"), (short)this.main.getConfig().getInt("BetTiles.1.Durability"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.1.Name")));
        itemMeta.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.1.Lore")), 0));
        itemStack.setItemMeta(itemMeta);
        ItemStack itemStack2 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.2.Item")), this.main.getConfig().getInt("BetTiles.2.Amount"), (short)this.main.getConfig().getInt("BetTiles.2.Durability"));
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.2.Name")));
        itemMeta2.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.2.Lore")), 0));
        itemStack2.setItemMeta(itemMeta2);
        ItemStack itemStack3 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.3.Item")), this.main.getConfig().getInt("BetTiles.3.Amount"), (short)this.main.getConfig().getInt("BetTiles.3.Durability"));
        ItemMeta itemMeta3 = itemStack3.getItemMeta();
        itemMeta3.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.3.Name")));
        itemMeta3.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.3.Lore")), 0));
        itemStack3.setItemMeta(itemMeta3);
        ItemStack itemStack4 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.4.Item")), this.main.getConfig().getInt("BetTiles.4.Amount"), (short)this.main.getConfig().getInt("BetTiles.4.Durability"));
        ItemMeta itemMeta4 = itemStack4.getItemMeta();
        itemMeta4.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.4.Name")));
        itemMeta4.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.4.Lore")), 0));
        itemStack4.setItemMeta(itemMeta4);
        ItemStack itemStack5 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.5.Item")), this.main.getConfig().getInt("BetTiles.5.Amount"), (short)this.main.getConfig().getInt("BetTiles.5.Durability"));
        ItemMeta itemMeta5 = itemStack5.getItemMeta();
        itemMeta5.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.5.Name")));
        itemMeta5.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.5.Lore")), 0));
        itemStack5.setItemMeta(itemMeta5);
        ItemStack itemStack6 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.6.Item")), this.main.getConfig().getInt("BetTiles.6.Amount"), (short)this.main.getConfig().getInt("BetTiles.6.Durability"));
        ItemMeta itemMeta6 = itemStack6.getItemMeta();
        itemMeta6.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.6.Name")));
        itemMeta6.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.6.Lore")), 0));
        itemStack6.setItemMeta(itemMeta6);
        ItemStack itemStack7 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.fill.Item")), this.main.getConfig().getInt("BetTiles.fill.Amount"), (short)this.main.getConfig().getInt("BetTiles.fill.Durability"));
        ItemMeta itemMeta7 = itemStack7.getItemMeta();
        itemMeta7.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.fill.Name")));
        itemMeta7.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.fill.Lore")), n));
        itemStack7.setItemMeta(itemMeta7);
        ItemStack itemStack8 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("BetTiles.info.Item")), this.main.getConfig().getInt("BetTiles.info.Amount"), (short)this.main.getConfig().getInt("BetTiles.info.Durability"));
        ItemMeta itemMeta8 = itemStack8.getItemMeta();
        itemMeta8.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("BetTiles.info.Name").replace("%b%", String.valueOf(n))));
        itemMeta8.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("BetTiles.info.Lore")), n));
        itemStack8.setItemMeta(itemMeta8);
        int n2 = 0;
        while (n2 < 27) {
            this.betGUI.setItem(n2, itemStack7);
            ++n2;
        }
        this.betGUI.setItem(10, itemStack);
        this.betGUI.setItem(11, itemStack2);
        this.betGUI.setItem(12, itemStack3);
        this.betGUI.setItem(13, itemStack8);
        this.betGUI.setItem(14, itemStack4);
        this.betGUI.setItem(15, itemStack5);
        this.betGUI.setItem(16, itemStack6);
    }
}

