package categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class ClassA {
  @Category(PerformanceTests.class)
  @Test
  public void test1() {
    System.out.println("a1");
    assertThat(1 == 1, is(true));
  }

  @Test
  public void test2() {
    System.out.println("a2");
    assertThat(1 == 1, is(true));
  }
}
