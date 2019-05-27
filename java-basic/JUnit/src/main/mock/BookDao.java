package mock;

import java.util.List;

public interface BookDao {
  List<Book> findBookByAuthor();
}
