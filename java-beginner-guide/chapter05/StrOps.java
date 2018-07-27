class StrOps {
  public static void main(String args[]) {
    String demo1 = new String("Java strings");
    String demo2 = "They are";
    String demo3 = new String(demo2);

    String str1 = "When it comes to Web programming";
    String str2 = new String(str1);
    String str3 = "Java string";
    int result, idx;
    char ch;

    System.out.println(str1.length());
    for (int i = 0; i < str1.length(); i++) {
      System.out.println(str1.charAt(i));
    }
    System.out.println();

    if (str1.equals(str2)) {
      System.out.println("str1 equals str2");
    } else {
      System.out.println("str1 does not equal str2");
    }

    System.out.println("Compare " + str1.compareTo(str3));

    str2 = "One two three One";

    idx = str2.indexOf("One");
    System.out.println("First: " + idx);
    idx = str2.lastIndexOf("One");
    System.out.println("Last: " + idx);


    String orgstr = "Java makes the Web move";

    String substr = orgstr.substring(5,18);
    System.out.println("org: " + orgstr);
    System.out.println("sub: " + substr);

    // String switch
    String command = "cancel";

    switch(command) {
      case "connect":
        System.out.println("Connection");
        break;
      case "cancel":
        System.out.println("Cancel");
        break;
    }
  }
}