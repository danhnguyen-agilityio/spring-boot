package singleparameter;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ParameterizedTest {
  @Parameterized.Parameter
  public String domainName;

  @Parameterized.Parameters
  public static Object[] data() {
    return new Object[] {
        "google.com",
        "facebook.com",
        "twitter.com"
    };
  }

  @Test
  public void testValidDomain() {
    System.out.println(domainName);
    assertTrue(DomainUtils.isValid(domainName));
  }

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(ParameterizedTest.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println(result.wasSuccessful());
  }
}
