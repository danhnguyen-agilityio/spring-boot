package chatper05;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RangeDemo {
  public static void main(String[] args) {
    IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n% 2 == 0);
    System.out.println(evenNumbers.count());

    Stream<int[]> triples = IntStream.rangeClosed(1, 100).boxed()
        .flatMap(a ->
            IntStream.rangeClosed(a, 100)
              .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
              .mapToObj(b -> new int[]{a,b,(int)Math.sqrt(a*a + b*b)})
        );

    Stream<double[]> triple2 = IntStream.rangeClosed(1, 100).boxed()
        .flatMap(a -> IntStream.rangeClosed(a, 100)
            .mapToObj(b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
            .filter(t -> t[2] % 1 == 0)
        );
  }
}
