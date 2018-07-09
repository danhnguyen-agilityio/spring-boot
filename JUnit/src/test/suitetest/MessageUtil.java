package suitetest;

/**
 * This class prints the given message on console
 */
public class MessageUtil {
  private String message;

  /**
   * Constructor
   * @param message to be printed
   */
  public MessageUtil(String message) {
    this.message = message;
  }

  public String printMessage() {
    return message;
  }

  public String salutationMessage() {
    message = "Hi!" + message;
    System.out.println(message);
    return message;
  }

  public void showMessage() {
    System.out.println(message);
//    while (true);
  }
}
