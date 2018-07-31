package behavior.memento.pattern.demo;

/**
 * Memento class
 */
class Memento {
  private String state;

  public Memento(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }
}

/**
 * Caretaker Class
 */
class Caretaker {
  private Memento memento;

  public void saveMemento(Memento m) {
    memento = m;
  }

  public Memento retrieveMemento() {
    return memento;
  }
}

/**
 * Originator class
 */
class Originator {
  private String state;
  Memento m;

  public void setState(String state) {
    this.state = state;
    System.out.println("State at present: " + state);
  }

  public Memento originatorMemento() {
    m = new Memento(state);
    return m;
  }

  /**
   * Back to old state
   */
  public void revert(Memento memento) {
    System.out.println("Restoring to previous state...");
    state = memento.getState();
    System.out.println("State at present: " + state);
  }
}

public class MementoPatternEx {
  public static void main(String[] args) {
    Originator o = new Originator();
    o.setState("First state");

    // Holding old state
    Caretaker c = new Caretaker();
    c.saveMemento(o.originatorMemento());

    // Change state
    o.setState("Second state");

    // Restore saved state
    o.revert(c.retrieveMemento());
  }
}
