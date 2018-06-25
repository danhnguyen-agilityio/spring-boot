package collection;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapDemo {
    public enum Days {
        Monday, Tuesday, Wednesday, Thursday
    }

    public static void main(String[] args) {
        EnumMap<Days, String> map = new EnumMap<>(Days.class);
        map.put(Days.Monday, "1");
        map.put(Days.Tuesday, "2");
        map.put(Days.Wednesday, "3");
        map.put(Days.Thursday, "4");

        for (Map.Entry m : map.entrySet()) {
            System.out.println(m.getKey() + "  " + m.getValue());
        }

    }
}
