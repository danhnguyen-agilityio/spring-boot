package collection;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

enum days {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

public class EnumSetDemo {
    public static void main(String[] args) {
        Set<days> set = EnumSet.of(days.WEDNESDAY, days.WEDNESDAY, days.TUESDAY);

//        for (days day : set) {
//            System.out.println(day);
//        }

        Iterator<days> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        Set<days> set1 = EnumSet.allOf(days.class);
        System.out.println("Week days:" + set1);

        Set<days> set2 = EnumSet.noneOf(days.class);
        System.out.println("Week days:" + set2);

    }
}
