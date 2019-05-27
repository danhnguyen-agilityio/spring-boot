/** Auto box in expression */
class AutoBoxExpression {
  public static void main(String args[]) {
    Integer iOb, iOb2;
    int i;

    iOb = 99;

    // iOb is unboxed, the exp is evaluated, and the result is reboxed
    // and assigned yo iOb2
    iOb2 = iOb + (iOb / 3);
    System.out.println(iOb2);

    // The same exp is evaluated, but the result is not reboxed
    i = iOb + (iOb / 3);
    System.out.println(i);
  } 
}