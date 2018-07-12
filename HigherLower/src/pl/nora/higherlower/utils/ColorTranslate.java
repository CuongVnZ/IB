/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package pl.nora.higherlower.utils;

import java.util.ArrayList;
import org.bukkit.ChatColor;

public class ColorTranslate {
    public static String cc(String string) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string);
    }

    public static ArrayList<String> cc(ArrayList<String> arrayList, int n) {
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (String string : arrayList) {
            arrayList2.add(ColorTranslate.cc(string.replace("%b%", String.valueOf(n))));
        }
        return arrayList2;
    }
}

