/** MyFunc demo */
interface MyFunc {

  /** Abstract function */
  int func(int n);
}

class VarCapter{
  public static void main(String args[]) {
    // A local variable that can be captured
    int num = 10;

    MyFunc myLambda = (n) -> {
      int v = num + n;

      // Illegal because it attempts to modify the value of num
      // num++;
      v++;
      return v;
    };

    System.out.println(myLambda.func(10));

    // Illegal because it remove the effectively final status from num
    // num = 9;

  }
}