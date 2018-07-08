package pl.backup.api;

import java.util.List;
import org.bukkit.command.CommandSender;
import pl.backup.utils.Util;

public abstract class Command
  extends org.bukkit.command.Command
{
  private final String name;
  private final String usage;
  private final String desc;
  private final String permission;
  
  public Command(String paramString1, String paramString2, String paramString3, String paramString4, List<String> paramList)
  {
    super(paramString1, paramString2, paramString3, paramList);
    this.name = paramString1;
    this.usage = paramString3;
    this.desc = paramString2;
    this.permission = paramString4;
  }
  
  public boolean execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString)
  {
    if (((this.permission != null) || (this.permission != "")) && (!paramCommandSender.hasPermission(this.permission)))
    {
      String str = "Brak permisji";
      str = str.replace("{PERM}", getPermission());
      return Util.sendMsg(paramCommandSender, str);
    }
    return onExecute(paramCommandSender, paramArrayOfString);
  }
  
  public abstract boolean onExecute(CommandSender paramCommandSender, String[] paramArrayOfString);
  
  public String getName()
  {
    return this.name;
  }
  
  public String getUsage()
  {
    return this.usage;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public String getPermission()
  {
    return this.permission;
  }
}
