import java.util.Arrays;

/** Stack demo */
public class StackDemo {

  /** A generic method for reversing an array */
  public static <E> void reverse(E[] a) {
    Stack<E> buffer = new ArrayStack<>(a.length);
    for (int i = 0; i < a.length; i++)
      buffer.push(a[i]);
    for (int i = 0; i < a.length; i++)
      a[i] = buffer.pop();
  }

  /** Tests if delimiter in the given expression are properly matched */
  public static boolean isMatched(String expression) {
    final String opening = "({["; // opening delimiters
    final String closing = ")}]"; // respective closing delimiters
    Stack<Character> buffer = new LinkedStack<>();
    
    for (char c : expression.toCharArray()) {
      if (opening.indexOf(c) != -1) { // this is a left delimiter
        buffer.push(c);
      } else if (closing.indexOf(c) != -1) { // this is right delimiter
        if (buffer.isEmpty()) return false; // nothing to match with
        if (closing.indexOf(c) != opening.indexOf(buffer.pop())) return false; // mismatched delimiter
      }
    }
    return buffer.isEmpty(); // were all opening delimiter matched ?
  }

  /** Test if every opening tab has a matching closing tag in HTML string */
  public static boolean isHTMLMatched(String html) {
    Stack<String> buffer = new LinkedStack<>();
    int j = html.indexOf('<'); // find first '<' character
    while (j != -1) {
      int k = html.indexOf('>', j + 1); // find next '>' character
      if (k == -1) return false; // invalid tag
      String tag = html.substring(j + 1, k); //strip away <>
      if (!tag.startsWith("/")) buffer.push(tag); // this is opening tag
      else { // this is a closing tag
        if (buffer.isEmpty()) return false; // no tag to match
        if (!tag.substring(1).equals(buffer.pop())) return false; // mismatched tag
      }
      j = html.indexOf('<', k + 1); // find next '<' if any
    }
    return buffer.isEmpty(); // were all opening tags matched
  }

  public static void main(String args[]) {
    String[] s = {"a", "b", "c", "d"};

    reverse(s);
    System.out.println("s = " + Arrays.toString(s));

  }
}