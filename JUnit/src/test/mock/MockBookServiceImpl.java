package mock;

import java.util.ArrayList;
import java.util.List;

public class MockBookServiceImpl implements BookService {

  @Override
  public List<Book> findBookByAuthor(String author) {
    List<Book> books = new ArrayList<>();
    if ("Java".equals(author)) {
      books.add(new Book("Java in action"));
      books.add(new Book("bot"));
      books.add(new Book("Java data structure"));
    }
    return books;
  }
}
