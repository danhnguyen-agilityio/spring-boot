package behavior.strategy.pattern;

public class SecondChoice implements IChoice {
  @Override
  public void myChoice(String s1, String s2) {
    System.out.println("Concatenate the numbers");
    System.out.println("The result: " + s1 + s2);
  }
}
