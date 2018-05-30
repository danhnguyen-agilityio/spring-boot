/** Class use annotation deprecated */
@Deprecated
class MyClass {
  private String msg;

  /** Get message */
  @Deprecated
  String getMsg() {
    return "message";
  }
}

class AnnotationDemo {
  public static void main(String args[]) {
    MyClass myObj = new MyClass();

    System.out.println(myObj.getMsg());
  }
}