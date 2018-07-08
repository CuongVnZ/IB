package pl.backup.api;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.backup.utils.Util;

public abstract class PlayerCommand
  extends Command
{
  public PlayerCommand(String paramString1, String paramString2, String paramString3, String paramString4, List<String> paramList)
  {
    super(paramString1, paramString2, paramString3, paramString4, paramList);
  }
  
  public boolean onExecute(CommandSender p, String[] paramArrayOfString)
  {
    if (!(p instanceof Player)) {
      return Util.sendMsg(p, "&cIn-game command!");
    }
    return onCommand((Player)p, paramArrayOfString);
  }
  
  public abstract boolean onCommand(Player paramPlayer, String[] paramArrayOfString);
}
