package bookpackext;

/** Class BookInfo is subclass of Book */
class BookInfo extends bookpack.Book {
  private String publisher;

  public BookInfo(String t, String a, int d, String p) {
    super(t, a, d);
    publisher = p;
  }

  /** Show info */
  public void show() {
    super.show();
    System.out.println(publisher);
    System.out.println();
  }

  /** Get title */
  public String getTitle() {
    return title;
  }

  /** Set title */
  public void setTitle(String t) {
    title = t;
  }
}

class ProTectDemo {
  public static void main(String args[]) {
    BookInfo book = new BookInfo("Danh", "Tu", 12, "Viking");

    book.show();
    book.getTitle();

    // book.title = "test title"; // Error: title has protected access in Book
  }
}