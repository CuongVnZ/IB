package pl.backup;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import pl.backup.api.Command;
import pl.backup.api.CommandManager;
import pl.backup.commands.BackupCommand;
import pl.backup.interfaces.Store;
import pl.backup.listeners.ClickListener;
import pl.backup.listeners.PlayerDeathListener;
import pl.backup.managers.TimerManager;
import pl.backup.managers.UserManager;
import pl.backup.mysql.MySQL;
import pl.backup.utils.CheckTask;
import pl.backup.utils.Ticking;
import pl.backup.utils.TimeUtil;

@SuppressWarnings("unused")
public class Main
  extends JavaPlugin
{
  private static Main plugin;
  private static Store store;
  private static PluginManager pluginManager;
  
  public void onLoad()
  {
    plugin = this;
  }
  
  public void onEnable()
  {
    this.saveDefaultConfig();
    Config.load();
    new Ticking().start();
    registerListener();
    registerDatabase();
    registerCommand();
    registerTask();
    UserManager.setup();
  }
  
  public void onDisable()
  {
    Bukkit.getScheduler().cancelTasks(this);
    Bukkit.savePlayers();
    for (World localWorld : Bukkit.getWorlds()) {
      localWorld.save();
    }
    try
    {
      Thread.sleep(2000L);
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    plugin = null;
  }
  
  public static Main getPlugin()
  {
    return plugin;
  }
  
  protected boolean registerDatabase()
  {
    store = new MySQL(Config.DATABASEMYSQLHOST, Config.DATABASEMYSQLPORT, Config.DATABASEMYSQLUSER, Config.DATABASEMYSQLPASS, Config.DATABASEMYSQLNAME, Config.DATABASETABLEPREFIX);
    
    boolean bool = store.connect();
    if (bool)
    {
      store.update(true, "CREATE TABLE IF NOT EXISTS `{P}backups` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,`uuid` varchar(255) NOT NULL,`time` bigint(22) NOT NULL,`armor` text NOT NULL,`inventory` text NOT NULL,`enderchest` text NOT NULL,`location` varchar(255) NOT NULL,`killer` varchar(255) NOT NULL);");
      store.update(true, "CREATE TABLE IF NOT EXISTS `{P}users` (`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,`uuid` varchar(255) NOT NULL,`name` varchar(25) NOT NULL,`backups` int(10) NOT NULL);");
      
      return bool;
    }
    return bool;
  }
  
  public static void registerCommand(Command paramCommand)
  {
    CommandManager.register(paramCommand);
  }
  
  public static void registerListener(Plugin paramPlugin, Listener... paramVarArgs)
  {
    if (pluginManager == null) {
      pluginManager = Bukkit.getPluginManager();
    }
    Listener[] arrayOfListener;
    int j = (arrayOfListener = paramVarArgs).length;
    for (int i = 0; i < j; i++)
    {
      Listener localListener = arrayOfListener[i];
      pluginManager.registerEvents(localListener, paramPlugin);
    }
  }
  
  public static Store getStore()
  {
    return store;
  }
  
  public void registerCommand()
  {
    registerCommand(new BackupCommand());
  }
  
  public void registerTask()
  {
    if (Config.CreateTask.booleanValue()) {
      new CheckTask().runTaskTimerAsynchronously(this, TimeUtil.MINUTE.getTime(Config.CreateTaskTime), TimeUtil.MINUTE.getTime(Config.CreateTaskTime));
    }
  }
  
  public void registerListener()
  {
    registerListener(this, new Listener[] { new TimerManager() });
    registerListener(this, new Listener[] { new PlayerDeathListener() });
    registerListener(this, new Listener[] { new ClickListener() });
  }
}
