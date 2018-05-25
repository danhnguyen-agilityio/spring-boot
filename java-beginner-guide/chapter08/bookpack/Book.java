package bookpack; // A short package demonstration

/** Class Book is part of bookpack */
public class Book {
  private String title;
  private String author;

  public Book(String t, String a) {
    title = t;
    author = a;
  }

  public void show() {
    System.out.println(title);
    System.out.println(author);
  }
}