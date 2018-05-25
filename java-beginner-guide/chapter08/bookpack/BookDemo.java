package bookpack; // A short package demonstration

/** Class Book is part of bookpack */
class Book {
  private String title;
  private String author;

  Book(String t, String a) {
    title = t;
    author = a;
  }

  void show() {
    System.out.println(title);
    System.out.println(author);
  }
}

/** Class BookDemo is part of bookpack */
class BookDemo {
  public static void main(String args[]) {
    Book book = new Book("Danh", "Tu");

    book.show();
  }
}