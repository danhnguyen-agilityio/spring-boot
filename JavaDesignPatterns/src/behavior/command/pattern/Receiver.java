package behavior.command.pattern;

public class Receiver {
  public void performUndo() {
    System.out.println("Undo");
  }

  public void performRedo() {
    System.out.println("Redo");
  }
}
