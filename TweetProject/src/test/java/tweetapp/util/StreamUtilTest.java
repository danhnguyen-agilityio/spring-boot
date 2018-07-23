package tweetapp.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class StreamUtilTest {

  /**
   * Test reverse stream
   */
  @Test
  public void reverse() {
    List<String> data =  Arrays.asList("David", "Lucas", "Beck", "Steven", "Rose");
    List<String> reversedData = StreamUtil.reverse(data.stream()).collect(Collectors.toList());

    // Check size
    assertEquals(5, reversedData.size());

    // Check first element
    assertEquals("Rose", reversedData.get(0));

    // Check last element
    assertEquals("David", reversedData.get(reversedData.size() - 1));

  }
}