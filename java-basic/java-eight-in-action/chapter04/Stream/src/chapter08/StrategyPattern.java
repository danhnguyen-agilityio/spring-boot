package chapter08;

public class StrategyPattern {

  interface ValidationStrategy {
    boolean execute(String s);
  }

  class IsAllLowerCase implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
      return s.matches("[a-z]+");
    }
  }

  class IsNumeric implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
      return s.matches("\\d+");
    }
  }

  class Validatior {
    private final ValidationStrategy strategy;

    public Validatior(ValidationStrategy strategy) {
      this.strategy = strategy;
    }

    public boolean validate(String s) {
      return strategy.execute(s);
    }
  }

  public void validator() {
    Validatior numericValidator = new Validatior((String s) -> s.matches("\\d+"));
    boolean s1 = numericValidator.validate("aaa");

    Validatior lowercaseValidator = new Validatior((String s) -> s.matches("[a-z]+"));
    boolean s2 = lowercaseValidator.validate("bbb");
  }

  public static void main(String[] args) {
    StrategyPattern strategyPattern = new StrategyPattern();
    strategyPattern.validator();
  }
}
