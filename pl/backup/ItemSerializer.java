/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.serialization.ConfigurationSerialization
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package pl.backup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.backup.utils.Logger;

@SuppressWarnings("unused")
public class ItemSerializer {
    public static String itemsToString(ItemStack[] arritemStack) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(ItemSerializer.serializeItemStack(arritemStack));
            objectOutputStream.flush();
            return DatatypeConverter.printBase64Binary(byteArrayOutputStream.toByteArray());
        }
        catch (Exception exception) {
            Logger.exception(exception);
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    public static ItemStack[] stringToItems(String s) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(s));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return deserializeItemStack((Map<String, Object>[]) ois.readObject());
        } catch (Exception e) {
            Logger.exception(e);
        }
        return new ItemStack[]{new ItemStack(Material.AIR)};
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object>[] serializeItemStack(ItemStack[] items) {

        Map<String, Object>[] result = new Map[items.length];

        for (int i = 0; i < items.length; i++) {
            ItemStack is = items[i];
            if (is == null) {
                result[i] = new HashMap<>();
            } else {
                result[i] = is.serialize();
                if (is.hasItemMeta()) {
                    result[i].put("meta", is.getItemMeta().serialize());
                }
            }
        }

        return result;
    }

    private static ItemStack[] deserializeItemStack(Map<String, Object>[] arrmap) {
        ItemStack[] arritemStack = new ItemStack[arrmap.length];
        int n = 0;
        while (n < arritemStack.length) {
            Map<String, Object> map = arrmap[n];
            if (map.size() == 0) {
                arritemStack[n] = null;
            } else {
                try {
                    if (map.containsKey("meta")) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> im = new HashMap<>((Map<String, Object>) map.remove("meta"));
                        im.put("==", "ItemMeta");
                        ItemStack itemStack = ItemStack.deserialize(map);
                        itemStack.setItemMeta((ItemMeta)ConfigurationSerialization.deserializeObject(im));
                        arritemStack[n] = itemStack;
                    } else {
                        arritemStack[n] = ItemStack.deserialize(map);
                    }
                }
                catch (Exception exception) {
                    Logger.exception(exception);
                    arritemStack[n] = null;
                }
            }
            ++n;
        }
        return arritemStack;
    }
}

