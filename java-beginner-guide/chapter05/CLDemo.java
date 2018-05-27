/** Display all command-line info */
class CLDemo {
  public static void main(String args[]) {
    System.out.println(args.length);

    for (String arg: args) {
      System.out.println(arg);
    }
  }
}