package categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

@Category({PerformanceTests.class, RegressionTests.class})
public class ClassB {
  @Test
  public void test1() {
    System.out.println("b1");
    assertThat(1 == 1, is(true));
  }
}