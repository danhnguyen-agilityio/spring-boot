class Test {
  int a, b;

  Test (int i, int j) {
    a = i;
    b = j;
  }

  /** Change object passed */
  void change(Test ob) {
    ob.a = ob.a + ob.b;
    ob.b = -ob.b;
  }
}

/** Pass object ref in method */
class PassObRef {
  public static void main(String args[]) {
    Test ob = new Test(15, 20);

    System.out.println(ob.a + "   " + ob.b);

    ob.change(ob);

    System.out.println(ob.a + "   " + ob.b);
  }
}