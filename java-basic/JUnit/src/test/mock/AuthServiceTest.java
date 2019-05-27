package mock;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

  @Test
  public void testTotalBookByMock() {

    // Setup
    AuthServiceImpl obj = new AuthServiceImpl();
//    BookServiceImpl bookService = new BookServiceImpl();
//    bookService.setBookDao(new BookDaoImpl());
//    obj.setBookService(bookService);

    obj.setBookService(new MockBookServiceImpl());
    obj.setBookValidatorService(new FakeBookValidatorService());

    // Test method
    int qty = obj.getTotalBooks("Java");

    // Verify result
    assertThat(qty, is(2));
  }

  @Test
  public void testTotalBookByMockito() {
    // Setup
    List<Book> books = Arrays.asList(
        new Book("Java in action"),
        new Book("bot"),
        new Book("Java data structure")
    );

    BookServiceImpl mockito = mock(BookServiceImpl.class);
    when(mockito.findBookByAuthor("Java")).thenReturn(books);

    AuthServiceImpl obj = new AuthServiceImpl();
    obj.setBookService(mockito);
    obj.setBookValidatorService(new FakeBookValidatorService());

    int qty = obj.getTotalBooks("Java");

    assertThat(qty, is(2));
  }
}
