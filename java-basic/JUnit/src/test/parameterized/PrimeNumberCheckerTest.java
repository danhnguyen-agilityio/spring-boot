package parameterized;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PrimeNumberCheckerTest {

  private Integer inputNumber;
  private Boolean expectedResult;
  private PrimeNumberChecker primeNumberChecker;

  @Before
  public void initialize() {
    primeNumberChecker = new PrimeNumberChecker();
  }

  public PrimeNumberCheckerTest(Integer inputNumber, Boolean expectedResult) {
    this.inputNumber = inputNumber;
    this.expectedResult = expectedResult;
  }

  @Parameterized.Parameters
  public static Collection primeNumbers() {
    return Arrays.asList(new Object[][] {
        {2, true},
        {6, false},
        {19, true},
        {22, false},
        {23, true}
    });
  }

  // This test will run 4 times since we have 5 parameters defined
  @Test
  public void testPrimeNumberChecker() {
    System.out.println("Parameterized Number is :" + inputNumber);
    assertEquals(expectedResult, primeNumberChecker.validate(inputNumber));
  }

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(PrimeNumberCheckerTest.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println(result.wasSuccessful());
  }
}

