package behavior.state.pattern.demo;

abstract class RemoteControl {
  abstract void pressSwitch(TV context);
}

class On extends RemoteControl {
  @Override
  void pressSwitch(TV context) {
    System.out.println("I am already On. Going to be Off now.");
    context.setState(new Off());
  }
}

class Off extends RemoteControl {
  @Override
  void pressSwitch(TV context) {
    System.out.println("I am Off. Going to be On now");
    context.setState(new On());
  }
}

class TV {
  RemoteControl state;

  public RemoteControl getState() {
    return state;
  }

  public void setState(RemoteControl state) {
    this.state = state;
  }

  TV(RemoteControl state) {
    this.state = state;
  }

  void pressButton() {
    state.pressSwitch(this);
  }
}

public class StatePatternEx {
  public static void main(String[] args) {
    Off initialState = new Off();
    TV tv = new TV(initialState);
    tv.pressButton();
    tv.pressButton();
  }
}
