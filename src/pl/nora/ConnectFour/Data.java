/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 */
package pl.nora.ConnectFour;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.SQL.MySQL;
import pl.nora.ConnectFour.Utilities.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Data {
    public ConnectFour pl;
    public File dataFile;
    public FileConfiguration data;
    public HashSet<String> validProfiles;

    public Data(ConnectFour connectFour) {
        this.pl = connectFour;
        this.validProfiles = new HashSet<String>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.createPlayer(player);
        }
        if (!connectFour.getUtil().mysql.booleanValue()) {
            this.createFiles();
        }
    }

    public void createFiles() {
        this.dataFile = new File(this.pl.getDataFolder(), "data.yml");
        if (!this.dataFile.exists()) {
            this.dataFile.getParentFile().mkdirs();
            Data.copy(this.pl.getResource("data.yml"), this.dataFile);
        }
        this.data = new YamlConfiguration();
        try {
            this.data.load(this.dataFile);
        }
        catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    public static void copy(InputStream inputStream, File file) {
        try {
            int n;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] arrby = new byte[1024];
            while ((n = inputStream.read(arrby)) > 0) {
                fileOutputStream.write(arrby, 0, n);
            }
            fileOutputStream.close();
            inputStream.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void saveFiles() {
        try {
            this.data.save(this.dataFile);
        }
        catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

    public void addWin(Player player, Player player2) {
        this.executeCommands(player, player2);
        if (this.pl.getUtil().stats.booleanValue()) {
            if (this.pl.getUtil().mysql.booleanValue()) {
                this.createPlayer(player);
                this.pl.getSQL().updateSQL("UPDATE CONNECTFOUR SET wins='" + (this.getWins(player) + 1) + "' WHERE uuid='" + player.getUniqueId() + "'");
                return;
            }
            this.data.set(String.valueOf(player.getUniqueId().toString()) + ".Wins", (Object)(this.getWins(player) + 1));
            this.saveFiles();
        }
    }

    public void executeCommands(Player player, Player player2) {
        if (this.pl.getUtil().commands.booleanValue()) {
            for (String string : this.pl.getUtil().commandList) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), (String)string.replace("%PLAYER1%", player.getName()).replace("%PLAYER2%", player2.getName()));
            }
        }
    }

    public void addLoss(Player player) {
        if (this.pl.getUtil().stats.booleanValue()) {
            if (this.pl.getUtil().mysql.booleanValue()) {
                this.pl.getSQL().updateSQL("UPDATE CONNECTFOUR SET loss='" + (this.getLoss(player) + 1) + "' WHERE uuid='" + player.getUniqueId() + "'");
                return;
            }
            this.data.set(String.valueOf(player.getUniqueId().toString()) + ".Losses", (Object)(this.getLoss(player) + 1));
            this.saveFiles();
        }
    }

    public Integer getWins(Player player) {
        if (this.pl.getUtil().mysql.booleanValue()) {
            try {
                ResultSet resultSet = this.pl.getSQL().querySQL("SELECT * FROM CONNECTFOUR WHERE uuid='" + player.getUniqueId() + "'");
                if (resultSet.next()) {
                    return resultSet.getInt("wins");
                }
                return 0;
            }
            catch (SQLException sQLException) {
                sQLException.printStackTrace();
            }
        }
        return this.data.getInt(String.valueOf(player.getUniqueId().toString()) + ".Wins");
    }

    public Integer getLoss(Player player) {
        if (this.pl.getUtil().mysql.booleanValue()) {
            try {
                ResultSet resultSet = this.pl.getSQL().querySQL("SELECT * FROM CONNECTFOUR WHERE uuid='" + player.getUniqueId() + "'");
                if (resultSet.next()) {
                    return resultSet.getInt("loss");
                }
                return 0;
            }
            catch (SQLException sQLException) {
                sQLException.printStackTrace();
            }
        }
        return this.data.getInt(String.valueOf(player.getUniqueId().toString()) + ".Losses");
    }

    public void createPlayer(Player player) {
        if (this.pl.getUtil().stats.booleanValue() && !this.validProfiles.contains(player.getName())) {
            this.validProfiles.add(player.getName());
            if (this.pl.getUtil().mysql.booleanValue()) {
                try {
                    ResultSet resultSet = this.pl.getSQL().querySQL("SELECT * FROM CONNECTFOUR WHERE uuid='" + player.getUniqueId() + "'");
                    if (!resultSet.next()) {
                        this.pl.getSQL().updateSQL("INSERT INTO CONNECTFOUR VALUES ('0','" + player.getUniqueId() + "','0','0')");
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            this.createFiles();
            this.data.set(String.valueOf(player.getUniqueId().toString()) + ".Wins", (Object)0);
            this.data.set(String.valueOf(player.getUniqueId().toString()) + ".Losses", (Object)0);
            this.saveFiles();
        }
    }
}

