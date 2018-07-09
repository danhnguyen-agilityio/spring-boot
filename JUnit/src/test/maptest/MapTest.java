package maptest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class MapTest {
  @Test
  public void testAssertMap() {
    Map<String, String> map = new HashMap<>();
    map.put("j", "java");
    map.put("c", "c++");
    map.put("p", "python");
    map.put("n", "node");

    Map<String, String> expected = new HashMap<>();
    expected.put("n", "node");
    expected.put("c", "c++");
    expected.put("j", "java");
    expected.put("p", "python");

    // Test equal, ignore order
    assertThat(map, is(expected));

    // Test size
    assertThat(map.size(), is(4));
  }
}
