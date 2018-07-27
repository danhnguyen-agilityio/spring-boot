/** Varargs and overloading */
class VarArgs {
  static void vaTest(int ... v) {
    System.out.println("Int " + v.length + v);
  }

  static void vaTest(boolean ... v) {
    System.out.println("Boolean: " + v.length);
  }

  static void vaTest(String msg, int ... v) {
    System.out.println("Msg: " + msg + v.length);
  }

  public static void main(String args[]) {
    vaTest(1, 2, 3);
    vaTest(true, false, false);
    vaTest("Testing", 10, 20);
    // vaTest(); Error Ambiguous
  }
}