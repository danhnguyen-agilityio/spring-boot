package collection;


import java.util.Hashtable;
import java.util.Map;

public class HashtableDemo {
    public static void main(String[] args) {
        Map<Integer, String> map = new Hashtable<>();
        map.put(100, "AAA");
        map.put(102, "BBB");
        map.put(101, "DDD");
        map.put(103, "CCC");
        map.put(100, "EEE");

        for (Map.Entry m : map.entrySet()) {
            System.out.println(m.getKey() + "  " + m.getValue());
        }
    }
}
