package bookpackext;

/** Use the Book class from bookpack */
class UseBook {
  public static void main(String args[]) {
    bookpack.Book book = new bookpack.Book("Danh", "Tu");

    book.show();
  }
}