/** 
 * Looping demo
 * @author Danh Nguyen
 * @version 1.0
 */
class LoopingDemo {
  /** Print shape */
  static void printShape() {
    printRectangle();
    printBorderRectangle();
    printTriangle();
    printBorderTriangle();
    printMultiplicationTable();
  }

  /** Print rectangle */
  static void printRectangle() {
    System.out.println("Print rectangle");
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 20; j++) {
        System.out.print("*");
      }
      System.out.println();
    }
  }

  /** Print border rectangle */
  static void printBorderRectangle() {
    System.out.println("Print border rectangle");
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 20; j++) {
        if ((i == 1 || i == 2) && (j > 0 && j < 19)) {
          System.out.print(" ");
          continue;
        }
        System.out.print("*");
      }
      System.out.println();
    }
  }

  /** Print triangle */
  static void printTriangle() {
    System.out.println("Print triangle");
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j <= 13; j++) {
        if (j >= 6 - i && j <= 6 + i) {
          System.out.print("*");
          continue;
        }
        System.out.print(" ");
      }
      System.out.println();
    }
  }

  /** Print border triangle */
  static void printBorderTriangle() {
    System.out.println("Print border triangle");
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j <= 12; j++) {
        if (i == 6 || j == 6 - i || j == 6 + i) {
          System.out.print("*");
          continue;
        }
        System.out.print(" ");
      }
      System.out.println();
    }
  }

  /** Print multiplication table */
  static void printMultiplicationTable() {
    System.out.println("Print multiplication table");
    String result = "";
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 9; j++) {
        result = j + " x " + i + " = " + j * i;
        for (int k = result.length(); k < 12; k++){
          result += " ";
        }
        if (j != 9) {
          result += "| ";
        }
        System.out.print(result);
      }
      System.out.println();
    }
  }

}