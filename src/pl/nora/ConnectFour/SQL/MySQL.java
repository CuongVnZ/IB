/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package pl.nora.ConnectFour.SQL;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Utilities.Utilities;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class MySQL {
    private Connection con;
    private String host;
    private String user;
    private String pass;
    private String DBName;
    private int port;

    public MySQL(String string, String string2, String string3, int n, String string4) {
        this.host = string;
        this.user = string2;
        this.pass = string3;
        this.port = n;
        this.DBName = string4;
        this.con = this.createConnection();
    }

    public void close() {
        try {
            if (!this.con.isClosed()) {
                this.con.close();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected Connection createConnection() {
        try {
            Connection connection;
            this.con = connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.DBName, this.user, this.pass);
            System.out.println("[ConnectFour] Connected to MySQL!");
            if (!this.tableExists().booleanValue()) {
                this.createTable();
            }
            return this.con;
        }
        catch (Exception exception) {
            System.out.println("[ConnectFour] Couldn't connect to MySQL!");
            ConnectFour.i().getUtil().mysql = false;
            return null;
        }
    }

    protected void createTable() {
        String string = "CREATE TABLE CONNECTFOUR (id INTEGER not NULL AUTO_INCREMENT,  uuid VARCHAR(255),  wins INTEGER,  loss INTEGER,  PRIMARY KEY ( id ))";
        this.updateSQL(string);
    }

    protected Boolean tableExists() throws SQLException {
        String string = "SHOW TABLES LIKE 'CONNECTFOUR'";
        ResultSet resultSet = this.querySQL(string);
        try {
			return resultSet.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    protected Boolean checkConnection() {
        try {
			if (this.con != null && !this.con.isClosed()) {
			    return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }

    protected Boolean closeConnection() throws SQLException {
        if (this.checkConnection().booleanValue()) {
            this.con.close();
            return true;
        }
        return false;
    }

    public ResultSet querySQL(String string) throws SQLException {
        return this.con.createStatement().executeQuery(string);
    }

    public void updateSQL(final String string) {
        new BukkitRunnable(){

            public void run() {
                try {
                    if (!MySQL.this.checkConnection().booleanValue()) {
                        MySQL.this.createConnection();
                    }
                    MySQL.this.con.createStatement().executeUpdate(string);
                }
                catch (SQLException sQLException) {
                    sQLException.printStackTrace();
                }
            }
        }.runTaskAsynchronously((Plugin)ConnectFour.i());
    }

    public Connection getConnection() {
        return this.con;
    }

}

