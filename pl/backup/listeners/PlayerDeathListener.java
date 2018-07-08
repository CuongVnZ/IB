package pl.backup.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.backup.Config;
import pl.backup.managers.UserManager;
import pl.backup.object.User;
import pl.backup.utils.Util;




public class PlayerDeathListener
  implements Listener
{
  public PlayerDeathListener() {}
  
  @EventHandler
  public void onClicksgd(PlayerDeathEvent event)
  {
    Player p1 = event.getEntity();
    Player p2 = event.getEntity().getKiller();
    User localUser = UserManager.getUser(p1);
    if (Config.CreateDeath.booleanValue()) {
      if (p2 != null) {
        localUser.createBackup("Killed by " + p2.getName());
        Util.sendMsg(p1, Config.CreateDeathMessage);
      }
      else {
        localUser.createBackup("Death");
        Util.sendMsg(p1, Config.CreateDeathMessage);
      }
    }
  }
}
