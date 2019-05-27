package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        // ConcurrentHashMap
        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");
        map.put("6", "1");
        System.out.println("ConcurrentHashMap before iterator: " + map);

        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("3")) map.put(key + "new", "new3");
        }
        System.out.println("ConcurrentHashMap after iterator: " + map);

        // HashMap
        map = new HashMap<>();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");
        map.put("6", "1");
        System.out.println("HashMap before iterator: " + map);

        it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("3"))  {
                map.put(key + "new", "new3");
                break;
            }
        }
        System.out.println("HashMap after iterator: " + map);
    }
}
