package pl.backup.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pl.backup.Main;
import pl.backup.interfaces.Store;
import pl.backup.object.User;
import pl.backup.utils.Logger;


@SuppressWarnings("unused")
public final class UserManager
{
  public UserManager() {}
  
  public static User getUser(Object player2)
  {
    return (User)users.get(((Entity) player2).getUniqueId());
  }
  
  public static User getUser(UUID paramUUID) {
    return (User)users.get(paramUUID);
  }
  
  public static User getUser(String paramString) {
    for (User localUser : users.values()) {
      if (localUser.getName().equalsIgnoreCase(paramString)) {
        return localUser;
      }
    }
    return null;
  }
  
  public static User createUser(Player paramPlayer)
  {
    User localUser = new User(paramPlayer);
    users.put(paramPlayer.getUniqueId(), localUser);
    return localUser;
  }
  
  public static void joinToGame(Player paramPlayer) {
    User localUser = getUser(paramPlayer);
    if (localUser == null) {
      createUser(paramPlayer);
      return;
    }
  }
  
  public static void leaveFromGame(Player paramPlayer) {
    User localUser = getUser(paramPlayer);
    if (localUser == null) {
      Logger.severe(new String[] {"Dane uzytkownika '" + paramPlayer.getName() + "' przepadly!" });
      return;
    }
    localUser.update(true);
  }
  
  public static void setup() {
    ResultSet localResultSet = Main.getStore().query("SELECT * FROM `{P}users`");
    try {
      while (localResultSet.next()) {
        User localUser = new User(localResultSet);
        users.put(localUser.getUUID(), localUser);
      }
    }
    catch (SQLException localSQLException)
    {
      Logger.warning(new String[] {"An error occurred while loading users!", "Error: " + localSQLException.getMessage() });
      localSQLException.printStackTrace();
    }
  }
  
  public static HashMap<UUID, User> getUsers() {
    return users;
  }
  


  private static final HashMap<UUID, User> users = new HashMap<UUID, User>();
}
