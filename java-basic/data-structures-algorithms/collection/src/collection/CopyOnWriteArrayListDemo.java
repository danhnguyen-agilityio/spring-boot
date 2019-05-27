package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println("List is: " + list);
            String str = it.next();
            System.out.println(str);
            if (str.equals("2")) list.remove("5");
            if (str.equals("3")) list.add("3 found");
            if (str.equals("4")) list.set(1, "4");
        }
    }
}
