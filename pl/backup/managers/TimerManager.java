package pl.backup.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import pl.backup.managers.TimerManager.TimerTask;
import pl.backup.utils.TimerCallback;








@SuppressWarnings("unused")
public class TimerManager
  implements Listener
{
  private static final Map<UUID, TimerTask> tasks = new HashMap<UUID, TimerTask>();
  
  public TimerManager() {}
  
  public static class TimerTask extends BukkitRunnable {
    private UUID player;
    private TimerCallback<Player> call;
    
    public void run() {
      call.success(Bukkit.getPlayer(player));
      TimerManager.tasks.remove(player);
    }
    
    public void cancel(Player paramPlayer) {
      super.cancel();
      call.error(paramPlayer);
    }
    
    public TimerTask(UUID paramUUID, TimerCallback<Player> paramTimerCallback) {
      player = paramUUID;
      call = paramTimerCallback;
    }
    
    public UUID getPlayer() {
      return player;
    }
  }
}
