package behavior.command.pattern;

public class MyUndoCommand implements ICommand {
  private Receiver receiver;

  MyUndoCommand(Receiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public void Do() {
    receiver.performUndo();
  }
}
