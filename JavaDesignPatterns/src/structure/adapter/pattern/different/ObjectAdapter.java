package structure.adapter.pattern.different;

public class ObjectAdapter {
  IIntegerValue myInt;

  public ObjectAdapter(IIntegerValue myInt) {
    this.myInt = myInt;
  }

  public int getInteger() {
    return 2 + this.myInt.getInteger();
  }
}
