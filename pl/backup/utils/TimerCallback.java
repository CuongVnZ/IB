package pl.backup.utils;

public abstract interface TimerCallback<E>
{
  public abstract void success(E paramE);
  
  public abstract void error(E paramE);
}
