package collection;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

class Book {
    int id;
    String name;
    int quantity;

    public Book(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}

class QuantityCompare implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        if (o1.quantity > o2.quantity) {
            return 1;
        }
        if (o1.quantity < o2.quantity) {
            return -1;
        }
        return 0;
    }
}

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("B");
        treeSet.add("C");
        treeSet.add("A");

        for (String s : treeSet) {
            System.out.println(s);
        }

        QuantityCompare quantityCompare = new QuantityCompare();

        // Creating books
        TreeSet<Book> set = new TreeSet<>(quantityCompare);
        Book b1 = new Book(121, "Danh", 30);
        Book b2 = new Book(110, "Tu", 20);
        Book b3 = new Book(140, "DInh", 10);

        // Adding books to treeset
        set.add(b1);
        set.add(b2);
        set.add(b3);

        // Traversing TreeSet
        for (Book b : set) {
            System.out.println(b.id + " " + b.name + " " + b.quantity);
        }
    }

}
