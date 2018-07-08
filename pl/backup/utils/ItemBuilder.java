/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.potion.Potion
 */
package pl.backup.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import pl.backup.Config;
import pl.backup.object.User;
import pl.backup.object.UserBackup;
import pl.backup.utils.Util;

@SuppressWarnings({ "deprecation", "unused" })
public class ItemBuilder {
    private Material mat;
    private int amount;
    private final short data;
    private String title = null;
    private final List<String> lore = new ArrayList<String>();
    private final HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
    private Color color;
    private Potion potion;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int n) {
        this(material, n, (short)0);
    }

    public ItemBuilder(Material material, short s) {
        this(material, 1, s);
    }

    public ItemBuilder(Material material, int n, short i) {
        this.mat = material;
        this.amount = n;
        this.data = i;
    }

    public ItemBuilder setType(Material material) {
        this.mat = material;
        return this;
    }

    public ItemBuilder setTitle(String string) {
        this.title = string;
        return this;
    }

    public ItemBuilder addLores(List<String> list) {
        this.lore.addAll(list);
        return this;
    }

    public ItemBuilder addLore(String string) {
        this.lore.add(string);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int n) {
        if (this.enchants.containsKey((Object)enchantment)) {
            this.enchants.remove((Object)enchantment);
        }
        this.enchants.put(enchantment, n);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        if (!this.mat.name().contains("LEATHER_")) {
            throw new IllegalArgumentException("Can only dye leather armor!");
        }
        this.color = color;
        return this;
    }

    public ItemBuilder setPotion(Potion potion) {
        if (this.mat != Material.POTION) {
            this.mat = Material.POTION;
        }
        this.potion = potion;
        return this;
    }

    public ItemBuilder setAmount(int n) {
        this.amount = n;
        return this;
    }

    public ItemStack build() {
        Material material = this.mat;
        if (material == null) {
            material = Material.AIR;
            Bukkit.getLogger().warning("Null material!");
        }
        ItemStack itemStack = new ItemStack(this.mat, this.amount, this.data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (this.title != null) {
            itemMeta.setDisplayName(this.title);
        }
        if (!this.lore.isEmpty()) {
            itemMeta.setLore(this.lore);
        }
        if (itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)itemMeta).setColor(this.color);
        }
        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantments(this.enchants);
        if (this.potion != null) {
            this.potion.apply(itemStack);
        }
        return itemStack;
    }

    @SuppressWarnings("rawtypes")
	public ItemBuilder clone() {
        ItemBuilder itemBuilder = new ItemBuilder(this.mat);
        itemBuilder.setTitle(this.title);
        for (String object : this.lore) {
            itemBuilder.addLore(object);
        }
        for (Map.Entry entry : this.enchants.entrySet()) {
            itemBuilder.addEnchantment((Enchantment)entry.getKey(), (Integer)entry.getValue());
        }
        itemBuilder.setColor(this.color);
        itemBuilder.potion = this.potion;
        return itemBuilder;
    }

    public Material getType() {
        return this.mat;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean hasEnchantment(Enchantment enchantment) {
        return this.enchants.containsKey((Object)enchantment);
    }

    public int getEnchantmentLevel(Enchantment enchantment) {
        return this.enchants.get((Object)enchantment);
    }

    public HashMap<Enchantment, Integer> getAllEnchantments() {
        return this.enchants;
    }

    public boolean isItem(ItemStack itemStack) {
        return this.isItem(itemStack, false);
    }

    public boolean isItem(ItemStack itemStack, boolean bl) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemStack.getType() != this.getType()) {
            return false;
        }
        if (!itemMeta.hasDisplayName() && this.getTitle() != null) {
            return false;
        }
        if (!itemMeta.getDisplayName().equals(this.getTitle())) {
            return false;
        }
        if (!itemMeta.hasLore() && !this.getLore().isEmpty()) {
            return false;
        }
        if (itemMeta.hasLore()) {
            for (String string : itemMeta.getLore()) {
                if (this.getLore().contains(string)) continue;
                return false;
            }
        }
        for (Enchantment string : itemStack.getEnchantments().keySet()) {
            if (this.hasEnchantment((Enchantment)string)) continue;
            return false;
        }
        return true;
    }

    public static ItemStack itemStack(UserBackup userBackup, Player player) {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>();
        itemMeta.setDisplayName(Util.fixColor(Config.NameItemsB));
        Iterator<String> iterator = Config.LoreBackups.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            string = string.replace("{DATE}", Util.getDate(userBackup.getBackupTime()));
            string = string.replace("{NAME}", userBackup.getUser().getName());
            string = string.replace("{REASON}", userBackup.getKiller());
            arrayList.add(string);
        }
        itemMeta.setLore((List<String>)Util.fixColor(arrayList));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

