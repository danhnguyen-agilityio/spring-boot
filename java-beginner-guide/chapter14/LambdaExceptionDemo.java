import java.io.*;

/** Use I/O streams */
interface MyIOAction {
  boolean ioAction(Reader rdr) throws IOException;
}

/** Lambda exception */
class LambdaExceptionDemo {
  public static void main(String args[]) {

    // This block lambda could throw an IOException
    // Thus IOException must be specified in a throws clause of ioAction
    MyIOAction myIO = (rdr) -> {
      int ch = rdr.read();
      return true;
    };
  }
}