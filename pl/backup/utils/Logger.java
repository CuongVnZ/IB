package pl.backup.utils;

import java.util.logging.Level;

public final class Logger
{
  public Logger() {}
  
  public static void info(String... paramVarArgs) {
    String[] arrayOfString = paramVarArgs;int j = paramVarArgs.length; for (int i = 0; i < j; i++) { String str = arrayOfString[i];
      log(Level.INFO, str);
    }
  }
  
  public static void warning(String... paramVarArgs) {
    String[] arrayOfString = paramVarArgs;int j = paramVarArgs.length; for (int i = 0; i < j; i++) { String str = arrayOfString[i];
      log(Level.WARNING, str);
    }
  }
  
  public static void severe(String... paramVarArgs) {
    String[] arrayOfString = paramVarArgs;int j = paramVarArgs.length; for (int i = 0; i < j; i++) { String str = arrayOfString[i];
      log(Level.SEVERE, str);
    }
  }
  
  public static void log(Level paramLevel, String paramString) {
    pl.backup.Main.getPlugin().getLogger().log(paramLevel, paramString);
  }
  
  public static void exception(Throwable paramThrowable) {
    paramThrowable.printStackTrace();
  }
}
