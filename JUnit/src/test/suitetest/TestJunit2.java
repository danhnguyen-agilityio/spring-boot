package suitetest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestJunit2 {
  String message = "David";
  MessageUtil messageUtil = new MessageUtil(message);

  @Test(timeout = 1000)
  public void testSalutationMessage() {
    System.out.println("Inside testSalutationMessage()");
    message = "Hi!" + "David";
    assertEquals(message, messageUtil.salutationMessage());
  }

  @Test(timeout = 1000)
  public void testShowMessage() {
    System.out.println("Show message");
    messageUtil.showMessage();
  }

  @Test(expected = ArithmeticException.class)
  public void testDivideNumber() {
    System.out.println("Divide number");
    messageUtil.divideNumber();
  }
}
