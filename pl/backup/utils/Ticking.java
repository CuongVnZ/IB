package pl.backup.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import pl.backup.Main;







@SuppressWarnings("unused")
public class Ticking
  implements Runnable
{
  public Ticking()
  {
    lastPoll = System.nanoTime();
    (this.history = new LinkedList<Double>()).add(Double.valueOf(20.0D));
  }
  
  public void start() {
    Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this, 1000L, 50L);
  }
  
  public void run() {
    long l1 = System.nanoTime();
    long l2 = (l1 - lastPoll) / 1000L;
    if (l2 == 0L) {
      l2 = 1L;
    }
    if (history.size() > 10) {
      history.remove();
    }
    double d1 = 5.0E7D / l2;
    if (d1 <= 21.0D) {
      history.add(Double.valueOf(d1));
    }
    lastPoll = l1;
    double d2 = 0.0D;
    for (Double localDouble : history) {
      if (localDouble != null) {
        d2 += localDouble.doubleValue();
      }
    }
    df.setRoundingMode(RoundingMode.HALF_UP);
    result = df.format(d2 / history.size());
  }
  
  public static String getTPS() {
    return result;
  }
  

  private static DecimalFormat df = new DecimalFormat("#,###.##");
  private transient long lastPoll; private final LinkedList<Double> history; private static String result = "20.0";
}
