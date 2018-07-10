/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package pl.nora.ConnectFour.Utilities;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
public class ItemFactory {
    public static ItemStack createItem(Material material, Integer n, String string, String string2) {
        ItemStack itemStack = new ItemStack(material, 1, (short)n.byteValue());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(string);
        if (string2 != null) {
            @SuppressWarnings("unused")
			String[] arrstring;
            ArrayList<String> arrayList = new ArrayList<String>();
            String[] arrstring2 = arrstring = string2.split("%");
            int n2 = arrstring2.length;
            int n3 = 0;
            while (n3 < n2) {
                String string3 = arrstring2[n3];
                arrayList.add(string3);
                ++n3;
            }
            itemMeta.setLore(arrayList);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

