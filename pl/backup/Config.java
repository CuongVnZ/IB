package pl.backup;

import java.util.List;

public class Config
{
  public static String DATABASEMYSQLHOST;
  public static int DATABASEMYSQLPORT;
  public static String DATABASEMYSQLUSER;
  public static String DATABASEMYSQLPASS;
  public static String DATABASEMYSQLNAME;
  public static String DATABASETABLEPREFIX;
  public static String CommandName;
  public static String CommandPermission;
  public static List<String> CommandAliases;
  public static boolean BACKUPRESTORELOCATION;
  public static String RestorePlayer;
  public static String RestoreEnder;
  public static List<String> LoreBackups;
  public static String NameInventory;
  public static String DateLore;
  public static String NameLore;
  public static String NameItemsB;
  public static List<String> LoreItems;
  public static String NameInventoryItems;
  public static String NameItemsR;
  public static String DateLoreItems;
  public static String NameLoreItems;
  public static Boolean CreateDeath;
  public static Boolean CreateTask;
  public static String CreateDeathMessage;
  public static String CreateTaskMessage;
  public static int CreateTaskTime;
  public static String CommandsUsage;
  public static String CommandsAll;
  public static String CommandsPlayer;
  public static String CommandsPlayerIsOffline;
  public static String CommandsCreateby;
  public static int MaxBackups;
  
  static void load()
  {
    CommandsUsage = Main.getPlugin().getConfig().getString("Command.Usage");
    CommandsAll = Main.getPlugin().getConfig().getString("Command.MessageAll");
    CommandsPlayer = Main.getPlugin().getConfig().getString("Command.MessagePlayer");
    CommandsPlayerIsOffline = Main.getPlugin().getConfig().getString("Command.PlayerIsOffline");
    CommandsCreateby = Main.getPlugin().getConfig().getString("Command.Createby");
    
    CommandAliases = Main.getPlugin().getConfig().getStringList("Cmd.aliases");
    CommandPermission = Main.getPlugin().getConfig().getString("Cmd.permission");
    CommandName = Main.getPlugin().getConfig().getString("Cmd.name");
    
    DATABASETABLEPREFIX = Main.getPlugin().getConfig().getString("MySQL.Prefix");
    DATABASEMYSQLHOST = Main.getPlugin().getConfig().getString("MySQL.Host");
    DATABASEMYSQLPORT = Main.getPlugin().getConfig().getInt("MySQL.Port");
    DATABASEMYSQLNAME = Main.getPlugin().getConfig().getString("MySQL.Name");
    DATABASEMYSQLPASS = Main.getPlugin().getConfig().getString("MySQL.Password");
    DATABASEMYSQLUSER = Main.getPlugin().getConfig().getString("MySQL.User");
    
    BACKUPRESTORELOCATION = Main.getPlugin().getConfig().getBoolean("Backup.Location");
    RestorePlayer = Main.getPlugin().getConfig().getString("Backup.player");
    RestoreEnder = Main.getPlugin().getConfig().getString("Backup.ender");
    
    LoreBackups = Main.getPlugin().getConfig().getStringList("Backup.Lore");
    NameInventory = Main.getPlugin().getConfig().getString("Backup.Name");
    DateLore = Main.getPlugin().getConfig().getString("Backup.DataLore");
    NameLore = Main.getPlugin().getConfig().getString("Backup.NameLore");
    
    LoreItems = Main.getPlugin().getConfig().getStringList("Items.Lore");
    NameInventoryItems = Main.getPlugin().getConfig().getString("Items.Name");
    DateLoreItems = Main.getPlugin().getConfig().getString("Items.DataLore");
    NameLoreItems = Main.getPlugin().getConfig().getString("Items.NameLore");
    NameItemsB = Main.getPlugin().getConfig().getString("Backup.NameB");
    NameItemsR = Main.getPlugin().getConfig().getString("Items.NameR");
    
    MaxBackups = Main.getPlugin().getConfig().getInt("Backup.Max");
    CreateDeath = Boolean.valueOf(Main.getPlugin().getConfig().getBoolean("Create.Death.Enable"));
    CreateTask = Boolean.valueOf(Main.getPlugin().getConfig().getBoolean("Create.Task.Enable"));
    CreateDeathMessage = Main.getPlugin().getConfig().getString("Create.Death.Message");
    CreateTaskMessage = Main.getPlugin().getConfig().getString("Create.Task.Message");
    CreateTaskTime = Main.getPlugin().getConfig().getInt("Create.Task.Time");
  }
}
