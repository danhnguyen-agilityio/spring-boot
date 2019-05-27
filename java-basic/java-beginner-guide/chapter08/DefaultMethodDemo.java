interface MyIF {
  int getUserID();

  default int getAdminId() {
    return 200;
  }

  /** This is a static interface method */
  static int getUniversalId() {
    return 0;
  }
}

/** Impelement MYIF */
class MyIFImp implements MyIF {
  public int getUserID() {
    return 100;
  }
}

class DefaultMethodDemo {
  public static void main(String args[]) {
    MyIFImp obj = new MyIFImp();

    System.out.println(obj.getUserID() + "   " + obj.getAdminId());

    System.out.println(MyIF.getUniversalId());
  }
}