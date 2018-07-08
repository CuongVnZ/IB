package pl.backup.managers;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import pl.backup.object.UserBackup;



public class BackupManager
{
  public BackupManager() {}
  
  public static HashMap<UUID, UserBackup> getUsers()
  {
    return back;
  }
  
  public static UserBackup getUserBackup(Player paramPlayer) { return (UserBackup)back.get(paramPlayer.getUniqueId()); }
  
  public static UserBackup getUserBackup(UUID paramUUID) {
    return (UserBackup)back.get(paramUUID);
  }
  
  public static void addEc(Player paramPlayer1, Player paramPlayer2) { ec.put(paramPlayer1.getUniqueId(), paramPlayer2.getUniqueId()); }
  

  public static void removeEc(Player paramPlayer) { ec.remove(paramPlayer.getUniqueId()); }
  
  public static boolean isEc(Player paramPlayer) {
    if (ec.containsKey(paramPlayer.getUniqueId())) {
      return true;
    }
    return false;
  }
  
  private static HashMap<UUID, UserBackup> back = new HashMap<UUID, UserBackup>();
  private static HashMap<UUID, UUID> ec = new HashMap<UUID, UUID>();
}
