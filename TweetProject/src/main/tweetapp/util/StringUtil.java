package tweetapp.util;

public class StringUtil {

  /**
   * Get substring between two strings
   * @param open
   * @param close
   * @return Substring between two strings
   */
  public static String substringBetween(String string, String open, String close) {
    return string.substring(string.indexOf(open) + open.length(), string.lastIndexOf(close));
  }

  /**
   * Get substring between two same String
   * @param text
   * @param tag
   * @return Substring between two same String
   */
  public static String substringBetween(String text, String tag) {
    return substringBetween(text, tag, tag);
  }

}
