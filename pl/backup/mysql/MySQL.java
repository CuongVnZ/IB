package pl.backup.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.scheduler.BukkitRunnable;
import pl.backup.Main;
import pl.backup.interfaces.Callback;
import pl.backup.interfaces.Store;
import pl.backup.utils.Logger;
import pl.backup.utils.TimeUtil;
import pl.backup.utils.Timming;



public class MySQL
  implements Store
{
  private final String host;
  private final String user;
  private final String pass;
  private final String name;
  private final String prefix;
  private final int port;
  private Connection conn;
  private long time;
  private Executor executor;
  private AtomicInteger ai;
  
  public MySQL(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    host = paramString1;
    port = paramInt;
    user = paramString2;
    pass = paramString3;
    name = paramString4;
    prefix = paramString5;
    executor = Executors.newSingleThreadExecutor();
    time = System.currentTimeMillis();
    ai = new AtomicInteger();
    new BukkitRunnable() {
      public void run() {
        if (System.currentTimeMillis() - time > TimeUtil.SECOND.getTime(30)) {
          update(false, "DO 1");
        }
      }
    }.runTaskTimer(Main.getPlugin(), 600L, 600L);
  }
  
  public boolean connect() {
    Timming localTimming = new Timming("MySQL ping").start();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      Logger.info(new String[] {"jdbc:mysql://" + host + ":" + port + "/" + name, user, pass });
      conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name, user, pass);
      Logger.info(new String[] {"Connected to the MySQL server!", "Connection ping " + localTimming.stop().getExecutingTime() + "ms!" });
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException) {
      Logger.warning(new String[] {"JDBC driver not found!", "Error: " + localClassNotFoundException.getMessage() });
      localClassNotFoundException.printStackTrace();
    }
    catch (SQLException localSQLException) {
      Logger.warning(new String[] {"Can not connect to a MySQL server!", "Error: " + localSQLException.getMessage() });
      localSQLException.printStackTrace();
    }
    return false;
  }
  
  public void update(boolean paramBoolean, final String paramString) {
    time = System.currentTimeMillis();
    Runnable local2 = new Runnable() {
      public void run() {
        try {
          conn.createStatement().executeUpdate(paramString.replace("{P}", prefix));
        }
        catch (SQLException localSQLException) {
          Logger.warning(new String[] {"An error occurred with given query '" + paramString.replace("{P}", prefix) + "'!", "Error: " + localSQLException.getMessage() });
          localSQLException.printStackTrace();
        }
      }
    };
    if (paramBoolean) {
      local2.run();
    }
    else {
      executor.execute(local2);
    }
  }
  
  public ResultSet update(String str) {
    try {
      Statement stm = conn.createStatement();
      stm.executeUpdate(str.replace("{P}", prefix), 1);
      ResultSet rss = stm.getGeneratedKeys();
      if (rss.next()) {
        return rss;
      }
    }
    catch (SQLException localSQLException) {
      Logger.warning(new String[] {"An error occurred with given query '" + str.replace("{P}", prefix) + "'!", "Error: " + localSQLException.getMessage() });
      localSQLException.printStackTrace();
    }
    return null;
  }
  
  public void disconnect() {
    if (conn != null) {
      try {
        conn.close();
      }
      catch (SQLException localSQLException) {
        Logger.warning(new String[] {"Can not close the connection to the MySQL server!", "Error: " + localSQLException.getMessage() });
        localSQLException.printStackTrace();
      }
    }
  }
  
  public void reconnect() {
    connect();
  }
  
  public boolean isConnected() {
    try {
      return (!conn.isClosed()) || (conn == null);
    }
    catch (SQLException localSQLException) {
      localSQLException.printStackTrace(); }
    return false;
  }
  
  public ResultSet query(String paramString)
  {
    try {
      return conn.createStatement().executeQuery(paramString.replace("{P}", prefix));
    }
    catch (SQLException localSQLException) {
      Logger.warning(new String[] {"An error occurred with given query '" + paramString.replace("{P}", prefix) + "'!", "Error: " + localSQLException.getMessage() });
      localSQLException.printStackTrace(); }
    return null;
  }
  












  public void query(final String paramString, final Callback<ResultSet> paramCallback)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          ResultSet localResultSet = conn.createStatement().executeQuery(paramString.replace("{P}", prefix));
          paramCallback.done(localResultSet);
        }
        catch (SQLException localSQLException) {
          Logger.warning(new String[] {"An error occurred with given query '" + paramString.replace("{P}", prefix) + "'!", "Error: " + localSQLException.getMessage() });
          paramCallback.error(localSQLException);
          localSQLException.printStackTrace();
        }
      }
    }, "MySQL Thread #" + ai.getAndIncrement()).start();
  }
  
  public Connection getConnection() {
    return conn;
  }
}
