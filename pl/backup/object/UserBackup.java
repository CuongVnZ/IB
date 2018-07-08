package pl.backup.object;

import java.sql.ResultSet;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.backup.ItemSerializer;
import pl.backup.Main;
import pl.backup.interfaces.Store;

@SuppressWarnings("unused")
public class UserBackup
{
  private User user;
  private UUID uuid;
  private long backupTime;
  private ItemStack[] armorBackup;
  private ItemStack[] inventoryBackup;
  private ItemStack[] enderchestBackup;
  private Location location;
  private String killer;
  
  public UserBackup(User paramUser, long paramLong, String paramString)
  {
    user = paramUser;
    
    uuid = paramUser.getUUID();
    backupTime = paramLong;
    armorBackup = user.getPlayer().getInventory().getArmorContents();
    inventoryBackup = user.getPlayer().getInventory().getContents();
    enderchestBackup = user.getPlayer().getEnderChest().getContents();
    location = user.getPlayer().getLocation();
    killer = paramString;
    insert();
  }
  
  public UserBackup(ResultSet paramResultSet) {
    try { user = pl.backup.managers.UserManager.getUser(UUID.fromString(paramResultSet.getString("uuid")));
      backupTime = paramResultSet.getLong("time");
      armorBackup = ItemSerializer.stringToItems(paramResultSet.getString("armor"));
      inventoryBackup = ItemSerializer.stringToItems(paramResultSet.getString("inventory"));
      enderchestBackup = ItemSerializer.stringToItems(paramResultSet.getString("enderchest"));
      String str = paramResultSet.getString("location");
      String[] arrayOfString = str.split(";");
      location = new Location(Bukkit.getWorld(arrayOfString[0]), Integer.parseInt(arrayOfString[1]), Integer.parseInt(arrayOfString[2]), Integer.parseInt(arrayOfString[3]));
      killer = paramResultSet.getString("killer");
    }
    catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  public void insert() {
    String str1 = String.valueOf(location.getWorld().getName()) + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    String str2 = "INSERT INTO `{P}backups`(`id`, `uuid`, `time`, `armor`, `inventory`, `enderchest`, `location`,`killer`) VALUES (NULL,'" + uuid + "','" + backupTime + "','" + ItemSerializer.itemsToString(armorBackup) + "','" + ItemSerializer.itemsToString(inventoryBackup) + "','" + ItemSerializer.itemsToString(enderchestBackup) + "', '" + str1 + "', '" + killer + "')";
    Main.getStore().update(false, str2);
  }
  
  public void update(boolean paramBoolean) {
    throw new IllegalArgumentException("Can not update that object!");
  }
  
  public void delete() {
    throw new IllegalArgumentException("Can not delete that object!");
  }
  
  public User getUser() {
    return user;
  }
  
  public Player getPlayer() { return Bukkit.getPlayer(uuid); }
  
  public long getBackupTime()
  {
    return backupTime;
  }
  
  public ItemStack[] getArmorBackup() {
    return armorBackup;
  }
  
  public ItemStack[] getInventoryBackup() {
    return inventoryBackup;
  }
  
  public ItemStack[] getEnderchestBackup() {
    return enderchestBackup;
  }
  
  public UUID getUUID() {
    return uuid;
  }
  
  public Location getLocation() { return location; }
  
  public void setUser(User paramUser) {
    user = paramUser;
  }
  
  public void setBackupTime(long paramLong) {
    backupTime = paramLong;
  }
  
  public void setArmorBackup(ItemStack[] paramArrayOfItemStack) {
    armorBackup = paramArrayOfItemStack;
  }
  
  public void setInventoryBackup(ItemStack[] paramArrayOfItemStack) {
    inventoryBackup = paramArrayOfItemStack;
  }
  
  public void setEnderchestBackup(ItemStack[] paramArrayOfItemStack) {
    enderchestBackup = paramArrayOfItemStack;
  }
  
  public void setKiller(String paramString) {
    killer = paramString;
  }
  
  public void setLocation(Location paramLocation) {
    location = paramLocation;
  }
  

  public String getKiller()
  {
    return killer;
  }
}
