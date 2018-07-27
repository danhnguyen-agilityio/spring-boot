package singleton.pattern.demo;

public class MakeACaptain {
  // Make the private to prevent the use of "new"
  private MakeACaptain() {}

  private static class SingletonHelper {
    // nested class is referenced after getCaptain() is called
    private static final MakeACaptain captain = new MakeACaptain();
  }

  public static MakeACaptain getCaptain() {
    return MakeACaptain.SingletonHelper.captain;
  }
}
