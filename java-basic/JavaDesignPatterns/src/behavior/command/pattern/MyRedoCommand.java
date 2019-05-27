package behavior.command.pattern;

public class MyRedoCommand implements ICommand {
  private Receiver receiver;

  MyRedoCommand(Receiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public void Do() {
    receiver.performRedo();
  }
}
