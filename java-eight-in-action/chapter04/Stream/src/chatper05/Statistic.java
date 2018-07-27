package chatper05;

import java.util.*;
import java.util.stream.Collectors;

public class Statistic {
  public static void main(String[] args) {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario","Milan");
    Trader alan = new Trader("Alan","Cambridge");
    Trader brian = new Trader("Brian","Cambridge");

    List<Transaction> transactions = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );

    List<Transaction> tr2011 = transactions.stream()
        .filter(transaction -> transaction.getYear() == 2011)
        .sorted(Comparator.comparing(Transaction::getValue))
        .collect(Collectors.toList());

    List<String> cities = transactions.stream()
        .map(transaction -> transaction.getTrader().getCity())
        .distinct()
        .collect(Collectors.toList());

    Set<String> cities2 = transactions.stream()
        .map(transaction -> transaction.getTrader().getCity())
        .collect(Collectors.toSet());

    List<Trader> traders = transactions.stream()
        .map(Transaction::getTrader)
        .filter(trader -> trader.getCity().equals("Cambridge"))
        .distinct()
        .sorted(Comparator.comparing(Trader::getName))
        .collect(Collectors.toList());

    String traderStr = transactions.stream()
        .map(transaction -> transaction.getTrader().getName())
        .distinct()
        .sorted()
        .reduce("", (n1, n2) -> n1 + n2);

    boolean milanBased = transactions.stream()
        .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

    transactions.stream()
        .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
        .map(Transaction::getValue) // getValue
        .forEach(System.out::println); //  d -> System.out::println(d)

    Optional<Integer> highestValue =
        transactions.stream()
        .map(Transaction::getValue)
        .reduce(Integer::max);

    Optional<Transaction> smallestTransaction =
        transactions.stream()
        .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);

    Optional<Transaction> smallestTransaction2 = transactions.stream()
        .min(Comparator.comparing(Transaction::getValue));
  }
}
