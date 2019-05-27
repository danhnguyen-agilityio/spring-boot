package categories;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(PerformanceTestSuite.class);
    Result result2 = JUnitCore.runClasses(RegressionTestSuite.class);
    Result result3 = JUnitCore.runClasses(ExcludePerformanceTestSuite.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println(result.wasSuccessful());
  }
}
