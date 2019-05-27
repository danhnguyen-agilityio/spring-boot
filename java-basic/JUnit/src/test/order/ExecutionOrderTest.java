package order;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.MethodSorters;
import suitetest.JunitTestSuite;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExecutionOrderTest {
  @Test
  public void testB() {
    assertThat(1 + 1, is(2));
  }

  @Test
  public void test1() {
    assertThat(1 + 1, is(2));
  }

  @Test
  public void testA() {
    assertThat(1 + 1, is(2));
  }

  @Test
  public void test2() {
    assertThat(1 + 1, is(2));
  }

  @Test
  public void testC() {
    assertThat(1 + 1, is(2));
  }

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(ExecutionOrderTest.class);

    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }

    System.out.println(result.wasSuccessful());
  }
}
