package tweetapp.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

  @Test
  public void testSubstringBetween() {
    String expected = "5b4c63aa170bb8185792506c";
    String text = "ObjectId(\"5b4c63aa170bb8185792506c\")";

    // Test for method substringBetween(String text, String tag)
    String tag = "\"";
    String actual = StringUtil.substringBetween(text, tag);
    assertEquals(expected, actual);

    // Test for method substringBetween(String string, String open, String close)
    String open = "(\"";
    String close = "\")";
    String actual1 = StringUtil.substringBetween(text, open, close);
    assertEquals(expected, actual1);

  }
}