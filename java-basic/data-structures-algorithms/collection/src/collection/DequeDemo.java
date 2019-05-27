package collection;

import java.util.Deque;
import java.util.LinkedList;

public class DequeDemo {
    public static void main(String[] args) {
        // Stack demo
        Deque<String> stack = new LinkedList<>();
        stack.push("B");
        stack.push("D");
        stack.push("C");
        stack.push("A");
        stack.push("E");
        stack.push("E");

        for (String s : stack) {
            System.out.println(s);
        }

        System.out.println("-------------------");
        // Queue demo
        Deque<String> queue = new LinkedList<>();
        queue.offer("B");
        queue.offer("D");
        queue.offer("C");
        queue.offer("A");
        queue.offer("E");
        queue.offer("E");

        for (String s : queue) {
            System.out.println(s);
        }
    }
}
