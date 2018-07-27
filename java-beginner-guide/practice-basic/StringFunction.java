/** 
 * This class demo use functions of String
 * @author Danh Nguyen
 * @version 1.0
 */
class StringFunction {
  /** Test function of String */
  static void test() {
    String str1 = "";
    String str2 = "compare";
    String str3 = "compare";
    String str4 = "Fucntion";
    String str5 = "  Hello   name    ";
    StringBuffer sb = new StringBuffer("abcde");

    if (str1 != null && str1.isEmpty()) {
      System.out.println(str1 + " is empty string");
    }

    if (str2.compareTo(str3) == 0) {
      System.out.println(str2 + " and " + str2 + " equal");
    }

    System.out.println(str2 + " concat " + str4 + " is " + str2.concat(str4) );

    System.out.println("Copy of " + str4 + " is " + new String(str4));

    System.out.println("str2 equal str4: " + str2.equals(str4));

    System.out.println("Demo join: " + String.join("Welcome", "to", "java"));

    System.out.println("Legnth s4: " + str4.length());

    System.out.println("Use insert in string buffer: " + sb.insert(3, "123"));

    System.out.println("Substring of str4: " + str4.substring(0, 4));

    System.out.println("Lower case of str4: " + str4.toLowerCase());

    System.out.println("Upper case of str4: " + str4.toUpperCase());

    System.out.println("Trim str5: " + str5.trim());
  }
}