package assertion;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

public class TestAssertions {
  @Test
  public void testAssertions() {
    String str1 = new String("abc");
    String str2 = new String("abc");
    String str3 = null;
    String str4 = "abc";
    String str5 = "abc";

    int val1 = 5;
    int val2 = 6;

    String[] expectedArray = {"one", "two", "three"};
    String[] resultArray = {"one", "two", "three"};

    // Check that two objects are equal
    assertEquals(str1, str2);

    // CHeck that a condition is true
    assertTrue(val1 < val2);

    //Check that a condition is false
    assertFalse(val1 > val2);

    // Check that an object isn't null
    assertNotNull(str1);

    // Check that an object is null
    assertNull(str3);

    // Check if two object ref point to the same object
    assertSame(str4, str5);

    // Check if two object ref not point to the same object
    assertNotSame(str1, str2);

    // Check whether two arrays are equal to each other
    assertArrayEquals(expectedArray, resultArray);
  }

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(TestAssertions.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println(result.wasSuccessful());
  }
}
