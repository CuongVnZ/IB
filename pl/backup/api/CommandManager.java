package pl.backup.api;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.backup.utils.Reflection;
import pl.backup.utils.Reflection.FieldAccessor;

@SuppressWarnings("unused")
public class CommandManager
{
  public static void register(Command cmd)
  {
    if (cmdMap == null) {
      cmdMap = (CommandMap)f.get(Bukkit.getServer().getPluginManager());
    }
    cmdMap.register(cmd.getName(), cmd);
    commands.put(cmd.getName(), cmd);
  }
  
  private static final HashMap<String, Command> commands = new HashMap<String, Command>();
  private static final Reflection.FieldAccessor<SimpleCommandMap> f = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
  private static CommandMap cmdMap = (CommandMap)f.get(Bukkit.getServer().getPluginManager());
}
