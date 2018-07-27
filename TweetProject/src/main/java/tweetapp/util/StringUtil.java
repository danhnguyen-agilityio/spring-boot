package tweetapp.util;

public class StringUtil {

  /**
   * Get substring between two strings
   * @param text Text need check
   * @param open open string
   * @param close close string
   * @return Substring between two strings
   */
  public static String substringBetween(String text, String open, String close) {
    return text.substring(text.indexOf(open) + open.length(), text.lastIndexOf(close));
  }

  /**
   * Get substring between two same String
   * @param text Text need check
   * @param tag Tag cover result
   * @return Substring between two same String
   */
  public static String substringBetween(String text, String tag) {
    return substringBetween(text, tag, tag);
  }

}
