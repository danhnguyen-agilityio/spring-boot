package structure.adapter.pattern.different;

public class ClassAdapter extends IntegerValue {
  public int getInteger() {
    return 2 + super.getInteger();
  }
}
