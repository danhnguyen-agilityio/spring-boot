// Use XOR to encode and decode a message
class EnCode {
  public static void main(String args[]) {
    String msg = "This is a test";
    String encmsg = "";
    String decmsg = "";
    int key = 88;

    System.out.println("Original message: ");
    System.out.println(msg);

    for (int i = 0; i < msg.length(); i++) {
      encmsg = encmsg + (char) (msg.charAt(i) ^ key);
    }

    System.out.println("Encode msg: " + encmsg);

    // decode message
    for (int i = 0; i < msg.length; i++) {
      decmsg = decmsg + (char) (encmsg.charAt(i) ^ key);
    }

    System.out.println("Decoded message: " + decmsg);
  }
}