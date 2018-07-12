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
public class HigherLowerGUI {
    public Inventory higherLowerGUI;
    private HigherLower main = HigherLower.getInstance();
    public static HigherLowerGUI hlgui = new HigherLowerGUI();

    public static HigherLowerGUI getInstance() {
        return hlgui;
    }

    public void prepareHigherLowerGUI(int n) {
        this.higherLowerGUI = Bukkit.createInventory((InventoryHolder)null, (int)27, (String)ColorTranslate.cc(this.main.getConfig().getString("HigherLowerGUITitle")));
        ItemStack itemStack = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("HigherLowerTiles.higher.Item")), this.main.getConfig().getInt("HigherLowerTiles.higher.Amount"), (short)this.main.getConfig().getInt("HigherLowerTiles.higher.Durability"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("HigherLowerTiles.higher.Name")));
        itemMeta.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("HigherLowerTiles.higher.Lore")), n));
        itemStack.setItemMeta(itemMeta);
        ItemStack itemStack2 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("HigherLowerTiles.lower.Item")), this.main.getConfig().getInt("HigherLowerTiles.lower.Amount"), (short)this.main.getConfig().getInt("HigherLowerTiles.lower.Durability"));
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("HigherLowerTiles.lower.Name")));
        itemMeta2.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("HigherLowerTiles.lower.Lore")), n));
        itemStack2.setItemMeta(itemMeta2);
        ItemStack itemStack3 = new ItemStack(Material.valueOf((String)this.main.getConfig().getString("HigherLowerTiles.fill.Item")), this.main.getConfig().getInt("HigherLowerTiles.fill.Amount"), (short)this.main.getConfig().getInt("HigherLowerTiles.fill.Durability"));
        ItemMeta itemMeta3 = itemStack3.getItemMeta();
        itemMeta3.setDisplayName(ColorTranslate.cc(this.main.getConfig().getString("HigherLowerTiles.fill.Name")));
        itemMeta3.setLore(ColorTranslate.cc(new ArrayList<String>(this.main.getConfig().getStringList("HigherLowerTiles.fill.Lore")), n));
        itemStack3.setItemMeta(itemMeta3);
        int n2 = 0;
        while (n2 < 27) {
            this.higherLowerGUI.setItem(n2, itemStack3);
            ++n2;
        }
        this.higherLowerGUI.setItem(11, itemStack2);
        this.higherLowerGUI.setItem(15, itemStack);
    }
}

