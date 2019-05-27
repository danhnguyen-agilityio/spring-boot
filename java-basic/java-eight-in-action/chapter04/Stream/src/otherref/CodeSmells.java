package otherref;

import javax.swing.text.html.Option;
import java.util.*;

public class CodeSmells {
  public static void main(String[] args) {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    // Anonymous inner types
    list.sort(new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.length() - o2.length();
      }
    });
    list.sort((o1, o2) -> o1.length() - o2.length());
    System.out.println(list);

    list.sort(Comparator.comparingInt(String::length).reversed());
    System.out.println(list);


  }

  boolean nestedForIf() {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    // Nested for-if statements
    /*for (String string : list) {
      if (string.equals("david")) {
        return true;
      }
    }
    return false;*/

    return list.stream()
        .anyMatch(string -> string.equals("David"));
  }

  String getElementSmell() {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    for (String current : list) {
      if (current.equals("david")) {
        return current;
      }
    }
    return null;
  }

  String getElement() {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    return list.stream()
        .filter(current -> current.equals("david"))
        .findFirst()
        .orElse(null);
  }

  void removeElementSmell() {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
      String current = iterator.next();
      if (current.endsWith("jin")) {
        iterator.remove();
      }
    }
  }

  void removeElement() {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    list.removeIf(current -> current.endsWith("jin"));
  }

  public static String findStringSmell(String wanted) {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    return list.stream()
        .filter(current -> current.equals("jin"))
        .findFirst()
        .orElse(null);
  }

  public static Optional<String> findString(String wanted) {
    List<String> list = Arrays.asList("Danh", "Tu", "Dinh", "Hong", "David");

    return list.stream().filter(current -> current.equals(wanted)).findFirst();
  }

  public static String check(String wanted) {
//    String foundString = findStringSmell(wanted);
//    if (foundString == null) {
//      return "Did not find value";
//    } else  {
//      return foundString;
//    }
    return findString("david").orElse("Did not find value");
  }

}
