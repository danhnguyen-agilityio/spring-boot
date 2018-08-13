package patterns.proxy.machine;

public class NoQuarterState implements State {
  transient GumballMachine gumballMachine;

  @Override
  public void insertQuarter() {

  }

  @Override
  public void ejectQuarter() {

  }

  @Override
  public void turnCrank() {

  }

  @Override
  public void dispense() {

  }

  @Override
  public String toString() {
    return "No quarter state";
  }
}
