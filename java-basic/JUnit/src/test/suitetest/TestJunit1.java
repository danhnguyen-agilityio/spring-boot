package suitetest;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

@Ignore
public class TestJunit1 {
  String message = "David";
  MessageUtil messageUtil = new MessageUtil(message);

  @Test
  public void testPrintMessage() {
    System.out.println("Inside testPrintMessage()");
    assertEquals(message, messageUtil.printMessage());
  }
}
