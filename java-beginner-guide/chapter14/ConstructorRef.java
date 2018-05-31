/** MyFunc is a functional interface whose method returns a MyClass reference */
interface MyFunc {
  MyClass func(String s);
}

/** MyClass demo */
class MyClass {
  private String str;

  /** Constructor takes an argument */
  MyClass(String s) {
    str = s;
  }

  /** Default constructor */
  MyClass() {
    str = "";
  }

  /** Get string */
  String getStr() {
    return str;
  }
}

class ConstructorRef {
  public static void main(String args[]) {
    // Create a reference to the MyClass constructor
    MyFunc myClassCons = MyClass::new; // A constructor reference

    // Create an instace of MyClass via that constructor ref
    MyClass mc = myClassCons.func("Testing");

    System.out.println(mc.getStr());
  }
}