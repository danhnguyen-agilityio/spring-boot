package singleton.pattern.atm;

abstract class Factory {
  protected abstract StatementType createStatements(String selection);
}

class StatementFactory extends Factory {
  private static StatementFactory uniqueInstance;

  public static StatementFactory getUniqueInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new StatementFactory();
    }
    return uniqueInstance;
  }

  @Override
  protected StatementType createStatements(String selection) {
    if (selection.equalsIgnoreCase("detail")) {
      return new DetailStatement();
    } else if (selection.equalsIgnoreCase("mini")) {
      return new MiniStatement();
    }
    throw new IllegalArgumentException("Selection doesnt exist");
  }
}

interface StatementType {
  void print();
}

class DetailStatement implements StatementType {


  @Override
  public void print() {
    System.out.println("Detailed Statement Created");
  }
}

class MiniStatement implements StatementType {

  @Override
  public void print() {
    System.out.println("Mini Statement Created");
  }
}

public class Client {
  public static void main(String[] args) {
    Factory factory = new StatementFactory();
    StatementType type = factory.createStatements("detail");
    type.print();
  }
}
