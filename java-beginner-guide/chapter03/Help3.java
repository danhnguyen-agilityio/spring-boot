// Class system show syntax control statement
class Help3 {
  public static void main(String args[]) throws java.io.IOException {
    char choice, ignore;

    for(;;) {
      do {
        System.out.println("Help on:");
        System.out.println("1. if");
        System.out.println("2. switch");
        System.out.println("3. for");
        System.out.println("4. while");
        System.out.println("5. do-while");
        System.out.println("6. break");
        System.out.println("7. continue");
        System.out.println("Choose one: ");

        choice = (char) System.in.read();

        do {
          ignore = (char) System.in.read();
        } while (ignore != '\n');
      } while (choice < '1' | choice > '7' & choice != 'q');

      if (choice == 'q') break;

      System.out.println();

      switch(choice) {
        case '1':
          System.out.println("if");
          break;
        case '2':
          System.out.println("switch");
          break;
        case '3':
          System.out.println("for");
          break;
        case '4':
          System.out.println("while");
          break;
        case '5':
          System.out.println("do-while");
          break;
        case '6':
          System.out.println("break label");
          break;
        case '7':
          System.out.println("continue label");
          break;  
      }
    }
  }
}