package collection;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("D");
        list2.add("E");

        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String next =  iterator.next();
            System.out.println(next);
        }

        System.out.println("-------------------------");
        for (String obj : list) {
            System.out.println(obj);
        }

        System.out.println("--------------------------");
        list.addAll(list2);
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println("--------------------------");
        list.removeAll(list2);
        Iterator itr2 = list.iterator();
        while (itr2.hasNext()) {
            System.out.println(itr2.next());
        }

        System.out.println("--------------------------");
        list.retainAll(list2);
        itr2 = list.iterator();
        while (itr2.hasNext()) {
            System.out.println(itr2.next());
        }
    }
}

