package behavior.iterator.pattern.demo;

public class IteratorPatternEx {
  public static void main(String[] args) {
    ISubject science = new Science();
    ISubject art = new Arts();

    IIterator scienceIterator = science.CreateIterator();
    IIterator artIterator = art.CreateIterator();

    System.out.println("Science subjects: ");
    Print(scienceIterator);

    System.out.println("Art subjects: ");
    Print(artIterator);

  }

  public static void Print(IIterator iterator) {
    while (!iterator.isDone()) {
      System.out.println(iterator.next());
    }
  }
}
