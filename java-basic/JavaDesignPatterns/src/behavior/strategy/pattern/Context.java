package behavior.strategy.pattern;

public class Context {
  IChoice myC;

  public void SetChoice(IChoice ic) {
    myC = ic;
  }

  public void ShowChoice(String s1, String s2) {
    myC.myChoice(s1, s2);
  }
}
