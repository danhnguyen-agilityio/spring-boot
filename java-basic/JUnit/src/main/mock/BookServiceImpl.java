package mock;

import java.util.List;

public class BookServiceImpl implements BookService {
  private BookDao bookDao;

  public BookDao getBookDao() {
    return bookDao;
  }

  public void setBookDao(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  @Override
  public List<Book> findBookByAuthor(String author) {
    return bookDao.findBookByAuthor();
  }
}
