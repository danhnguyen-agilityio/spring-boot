package chatper05;

public class Transaction {
  private final Trader trader;
  private final int year;
  private final int value;

  public Transaction(Trader trader, int year, int value) {
    this.trader = trader;
    this.year = year;
    this.value = value;
  }

  public Trader getTrader() {
    return trader;
  }

  public int getYear() {
    return year;
  }

  public int getValue() {
    return value;
  }

  public Transaction clone(Transaction source) {
    return new Transaction(source.trader, 2019, 53453);
  }

  @Override
  public String toString() {
    return "{" + this.trader + ", " +
        "year: " + this.year + ", " +
        "value: " + this.value + "}";
  }
}
