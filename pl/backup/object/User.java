package pl.backup.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.backup.Config;
import pl.backup.Main;
import pl.backup.interfaces.Store;
import pl.backup.utils.Logger;



@SuppressWarnings("unused")
public class User
{
  private UUID uuid;
  private String name;
  private int backups;
  private UUID previewed;
  
  public User(Player paramPlayer)
  {
    uuid = paramPlayer.getUniqueId();
    name = paramPlayer.getName();
    backups = 0;
    previewed = null;
    insert();
  }
  
  public User(ResultSet paramResultSet) {
    try { uuid = UUID.fromString(paramResultSet.getString("uuid"));
      name = paramResultSet.getString("name");
      backups = paramResultSet.getInt("backups");
      previewed = null;
    }
    catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  public void insert() {
    String str = "INSERT INTO `{P}users`(`uuid`, `name`, `backups`) VALUES ('" + uuid + "','" + name + "','" + backups + "')";
    Main.getStore().update(false, str);
  }
  
  public void update(boolean paramBoolean) {
    Main.getStore().update(paramBoolean, "UPDATE `{P}users` SET `backups`='" + getBackups() + "' WHERE `uuid`='" + uuid + "'");
  }
  
  public Player getPreviewed() {
    if (previewed == null) {
      return null;
    }
    return Bukkit.getPlayer(previewed);
  }
  
  public void setPreviewed(Player paramPlayer) { previewed = paramPlayer.getUniqueId(); }
  
  public void delete()
  {
    throw new IllegalArgumentException("Can not delete that object!");
  }
  
  public String getName() {
    return name;
  }
  


  public int getBackups() { return backups; }
  
  public void addBackups(int paramInt) {
    backups += paramInt;
    update(false);
  }
  
  public Player getPlayer() { return Bukkit.getPlayer(uuid); }
  
  public void createBackup(String paramString) {
    ResultSet localResultSet = Main.getStore().query("SELECT * FROM {P}backups WHERE uuid = '" + uuid + "' ORDER BY 'time' ASC;");
    try {
      int i = getBackups();
      if (i == Config.MaxBackups) {
        while (localResultSet.next()) {
          if (localResultSet.isFirst()) {
            Main.getStore().update(false, "DELETE FROM `{P}backups` WHERE `time` = '" + localResultSet.getLong("time") + "'");
          }
        }
        new UserBackup(this, System.currentTimeMillis(), paramString);
      }
      else {
        new UserBackup(this, System.currentTimeMillis(), paramString);
        addBackups(1);
      }
    } catch (SQLException localSQLException) {
      Logger.warning(new String[] { "An error occurred while loading users!", "Error: " + localSQLException.getMessage() });
      localSQLException.printStackTrace();
    }
  }
  


  public UUID getUUID()
  {
    return uuid;
  }
}
