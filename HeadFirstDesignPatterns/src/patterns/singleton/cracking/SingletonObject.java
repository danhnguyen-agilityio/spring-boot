package patterns.singleton.cracking;

public class SingletonObject {
  private static SingletonObject ref;

  public SingletonObject() {
  }

  public static synchronized SingletonObject getSingletonObject() {
    if (ref == null) {
      ref = new SingletonObject();
    }
    return ref;
  }

  public Object clone() throws CloneNotSupportedException
  {throw new CloneNotSupportedException ();
  }

}
