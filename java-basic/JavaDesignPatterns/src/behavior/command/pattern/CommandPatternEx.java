package behavior.command.pattern;

/**
 * Client class
 */
public class CommandPatternEx {
  public static void main(String[] args) {
    Receiver receiver = new Receiver();
    Invoke invoke = new Invoke();
    MyUndoCommand myUndoCommand = new MyUndoCommand(receiver);
    MyRedoCommand redoCommand = new MyRedoCommand(receiver);
    invoke.ExecuteCommand(myUndoCommand);
    invoke.ExecuteCommand(redoCommand);
  }
}
