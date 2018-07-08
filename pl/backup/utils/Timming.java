package pl.backup.utils;

public class Timming
{
  private long startTime;
  private long endTime;
  private long nanoStartTime;
  private long nanoEndTime;
  private final String name;
  
  public Timming(String paramString) {
    name = paramString;
  }
  
  public Timming start() {
    startTime = System.currentTimeMillis();
    nanoStartTime = System.nanoTime();
    return this;
  }
  
  public Timming stop() {
    endTime = System.currentTimeMillis();
    nanoEndTime = System.nanoTime();
    return this;
  }
  
  public long getExecutingTime() {
    if ((startTime == 0L) || (endTime == 0L)) {
      return 0L;
    }
    return endTime - startTime;
  }
  
  public long getExecutingNanoTime() {
    if ((nanoStartTime == 0L) || (nanoEndTime == 0L)) {
      return 0L;
    }
    return nanoEndTime - nanoStartTime;
  }
  
  public String toString() {
    return name + " executing time: " + getExecutingTime() + "ms (" + getExecutingNanoTime() + "ns)";
  }
  
  public long getStartTime() {
    return startTime;
  }
  
  public long getEndTime() {
    return endTime;
  }
  
  public long getNanoStartTime() {
    return nanoStartTime;
  }
  
  public long getNanoEndTime() {
    return nanoEndTime;
  }
  
  public String getName() {
    return name;
  }
}
