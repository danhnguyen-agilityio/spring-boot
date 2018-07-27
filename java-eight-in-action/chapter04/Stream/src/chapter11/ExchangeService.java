package chapter11;

import static chapter11.Shop.delay;

public class ExchangeService {
  public enum Money {
    EUR, USD
  }

  public static double getRate(Money a, Money b) {
    delay();
    return 2;
  }
}
