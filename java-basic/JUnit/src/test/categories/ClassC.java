package categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ClassC {
  @Category({PerformanceTests.class, RegressionTests.class})
  @Test
  public void testC1() {
    System.out.println("c1");
    assertThat(1 == 1, is(true));
  }

  @Category(RegressionTests.class)
  @Test
  public void testC2() {
    System.out.println("c2");
    assertThat(1 == 1, is(true));
  }
}
