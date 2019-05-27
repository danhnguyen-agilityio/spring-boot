package collection;

import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        set.add("B");
        set.add("A");
        set.add("C");
        for (String s : set) {
            System.out.println(s);
        }
    }
}
