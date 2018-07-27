package singleton.pattern.demo;

public class SingletonPatternEx {
  public static void main(String[] args) {
    MakeACaptain c1 = MakeACaptain.getCaptain();
    MakeACaptain c2 = MakeACaptain.getCaptain();
    if (c1 == c2) {
      System.out.println("C1 and c2 same");
    }
  }
}
