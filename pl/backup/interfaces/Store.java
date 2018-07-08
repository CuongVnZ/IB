package pl.backup.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract interface Store
{
  public abstract Connection getConnection();
  
  public abstract boolean connect();
  
  public abstract void disconnect();
  
  public abstract void reconnect();
  
  public abstract boolean isConnected();
  
  public abstract ResultSet query(String paramString);
  
  public abstract void query(String paramString, Callback<ResultSet> paramCallback);
  
  public abstract void update(boolean paramBoolean, String paramString);
  
  public abstract ResultSet update(String paramString);
}
