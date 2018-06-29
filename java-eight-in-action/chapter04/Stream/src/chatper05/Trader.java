package chatper05;

public class Trader {

  private final String name;
  private final String city;

  public Trader(String name, String city) {
    this.name = name;
    this.city = city;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }

  public void print(Object o) {
    System.out.print(o);
  }

  @Override
  public String toString() {
    return "Trader: " + this.name + " in " + this.city;
  }
}
