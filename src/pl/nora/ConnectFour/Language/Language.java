/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package pl.nora.ConnectFour.Language;

import java.io.File;
import java.io.InputStream;

import net.md_5.bungee.api.ChatColor;
import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("unused")
public class Language {
    private ConnectFour pl;
    public File languageFile;
    public FileConfiguration language;

    public Language(ConnectFour connectFour) {
        this.pl = connectFour;
        this.createFiles();
    }

    public void createFiles() {
        this.languageFile = new File(this.pl.getDataFolder(), "language.yml");
        if (!this.languageFile.exists()) {
            this.languageFile.getParentFile().mkdirs();
            Data.copy(this.pl.getResource("language.yml"), this.languageFile);
        }
        this.language = new YamlConfiguration();
        try {
            this.language.load(this.languageFile);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void saveFiles() {
        try {
            this.language.save(this.languageFile);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String get(String string) {
        return ChatColor.translateAlternateColorCodes((char)'$', (String)this.language.getString(string).replaceAll("\"", ""));
    }

    public String getRP(String string, String string2) {
        return ChatColor.translateAlternateColorCodes((char)'$', (String)this.language.getString(string).replaceAll("\"", "").replace("%PLAYER%", string2));
    }
}

