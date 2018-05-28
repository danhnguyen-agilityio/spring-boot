import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/** Copy file */
class CopyFile {
  public static void main(String args[]) {
    int i;
    FileInputStream fin = null;
    FileOutputStream fout = null;

    if (args.length != 2) {
      System.out.println("Show file file name");
      return;
    }

    try {
      fin = new FileInputStream(args[0]);
      fout = new FileOutputStream(args[1], true);

      do {
        i = fin.read();
        if (i != -1) {
          fout.write(i);
        }
      } while (i != -1);
    } catch (FileNotFoundException exc) {
      System.out.println("File not found");
    } catch (IOException exc) {
      System.out.println("An I/O error");
    } finally {
      try {
        if (fin != null) {
          fin.close();
        }
      } catch (IOException exc) {
        System.out.println("error closing input file");
      }

      try {
        if (fout != null) {
          fout.close();
        }
      } catch (IOException exc) {
        System.out.println("error closing output file");
      }
    }
  }
}