// Demo bitwize not
class NotDemo {
  public static void main(String args[]) {
    byte b = -34;
    b = (byte) ~b; // must conversion in here because bitwize operator auomatically convert to int type

    System.out.println("b: " + b);
  }
}