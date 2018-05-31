/** StringFunc interface */
interface StringFunc {
  String func(String str);
}

class LambdaArgumentDemo {
  static String changeStr(StringFunc sf, String s) {
    return sf.func(s);
  }

  public static void main(String args[]) {
    String inStr = "Lambda Expression Expand Java";
    String outString;

    System.out.println("Here is input string: " + inStr);

    // Define a lambda expression that reverses the contents of a string
    // and assign it to a StringFunc reference variable
    StringFunc reverse = (str) -> {
      String result = "";
      for (int i = str.length() - 1; i >=0; i--) {
        result += str.charAt(i);
      }

      return result;
    };

    // Pass reverse to the first argument to changeStr()
    // Pass the input string as the second argument
    outString = changeStr(reverse, inStr);
    System.out.println("The string reversed: " + outString);

    // This lambda expression replaces spaces with hyphens
    // It is embedded directly in the call to changeStr()
    outString = changeStr((str) -> str.replace(' ', '-'), inStr);
    System.out.println("The string space repaced: " + outString);

  }
}