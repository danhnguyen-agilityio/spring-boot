package annotation;

import org.junit.*;
import org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JUnitAnnotation {
  @BeforeClass
  public static void beforeClass() {
    System.out.println("in before class");
  }

  @AfterClass
  public static void afterClass() {
    System.out.println("in after class");
  }

  @Before
  public void before() {
    System.out.println("in before");
  }

  @After
  public void after() {
    System.out.println("in after");
  }

  @Test
  public void test() {
    System.out.println("in test");
  }

  @Ignore
  public void ignore() {
    System.out.println("ignore");
  }

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(JUnitAnnotation.class);

    for  (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println(result.wasSuccessful());
  }
}
