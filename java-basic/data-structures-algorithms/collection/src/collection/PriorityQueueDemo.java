package collection;

import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        Queue<String> queue = new PriorityQueue<>();
        queue.offer("C");
        queue.offer("D");
        queue.offer("A");
        queue.offer("B");
        queue.offer("E");

        for (String e : queue) {
            System.out.println(e);
        }

        queue.poll();
        for (String e : queue) {
            System.out.println(e);
        }
    }
}
