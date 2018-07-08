package pl.backup.interfaces;

public abstract interface Callback<T>
{
  public abstract T done(T paramT);
  
  public abstract void error(Throwable paramThrowable);
}
