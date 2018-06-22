package collection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorDemo {
    public static void main(String[] args) {
        List<String> al = new ArrayList<>();
        al.add("A");
        al.add("B");
        al.add("C");
        al.add("D");

        ListIterator<String> itr = al.listIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        while (itr.hasPrevious()) {
            System.out.println(itr.previous());
        }
    }
}
