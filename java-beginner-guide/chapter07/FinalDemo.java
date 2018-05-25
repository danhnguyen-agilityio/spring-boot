/** Return a String object */
class ErrorMsg {
  // Error codes
  final int OUTERR = 0;
  final int INERR = 1;
  final int DISKERR = 2;
  final int INDEXERR = 3;

  String msgs[] = {
    "Output Error",
    "Input Error",
    "Disk Full",
    "Index Out of bounds"
  };

  /** Get error msg */
  String getErrorMsg(int i) {
    return msgs[i];
  }
}

/** Class final demo */
class FinalDemo {
  public static void main(String args[]) {
    ErrorMsg err = new ErrorMsg();

    System.out.println(err.getErrorMsg(err.OUTERR));
    System.out.println(err.getErrorMsg(err.DISKERR));
  }
}