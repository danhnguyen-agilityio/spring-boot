package bookpack; // A short package demonstration

/** Class Book is part of bookpack */
public class Book {
  protected String title;
  protected String author;
  protected int pubDate;

  public Book(String t, String a, int p) {
    title = t;
    author = a;
    pubDate = p;
  }

  public void show() {
    System.out.println(title);
    System.out.println(author);
    System.out.println(pubDate);
    System.out.println();
  }
}