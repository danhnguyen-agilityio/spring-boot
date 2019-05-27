package structure.adapter.pattern.different;

public class ClassAndObjectAdapter {
  public static void main(String[] args) {
    ClassAdapter ca1 = new ClassAdapter();
    System.out.println(ca1.getInteger());

    ObjectAdapter oa = new ObjectAdapter(new IntegerValue());
    System.out.println(oa.getInteger());
  }
}
