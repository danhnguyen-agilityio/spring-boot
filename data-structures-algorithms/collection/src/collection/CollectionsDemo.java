package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("C");
        list.add("Core Java");
        list.add("Advanced Java");
        System.out.println("Initial collection: " + list);
        Collections.addAll(list, "Servlet", "JSp");
        System.out.println("After adding: " + list);
        String[] arr = {"C#", "Net"};
        Collections.addAll(list, arr);
        System.out.println("After adding array: " + list);

        List<Integer> list1 = new ArrayList<>();
        list1.add(46);
        list1.add(13);
        list1.add(70);
        list1.add(20);
        System.out.println("Value of maximum: " + Collections.max(list1));
        System.out.println("Value of minimum: " + Collections.min(list1));
    }
}
