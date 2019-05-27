import java.io.*;

/** Read and write primitive data */
class RWData {
  public static void main(String args[]) {
    int i = 10;
    double d = 1023.56;
    boolean b = true;

    // Write some value
    try (DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("textdata"))) {
      dataOut.writeInt(i);
      dataOut.writeDouble(d);
      dataOut.writeBoolean(b);
      dataOut.writeDouble(12.2 * 7.4);
    } catch (IOException exc) {
      System.out.println("Write error");
      return;
    }

    System.out.println();

    try (DataInputStream dataIn = new DataInputStream(new FileInputStream("textdata"))) {
      System.out.println(dataIn.readInt());
      System.out.println(dataIn.readDouble());
      System.out.println(dataIn.readBoolean());
      System.out.println(dataIn.readDouble());
    } catch (IOException exc) {
      System.out.println("Read error");
      exc.printStackTrace();
    }
  }
}