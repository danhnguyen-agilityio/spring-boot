package com.agility.spring.security.acl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Resource
    private BookService bookService;

    @Resource
    private MutableAclService aclService;

    @Before
    public void init() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("user1", "pass1",
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))));

        aclService.deleteAcl(new ObjectIdentityImpl(Book.class, 1), true);
        aclService.deleteAcl(new ObjectIdentityImpl(Book.class, 2), true);

        Book book1 = new Book(1L, "text");
        bookService.createBook(book1);

        bookService.grantPermission("user1", book1,
            new BookService.Permission[]{BookService.Permission.READ, BookService.Permission.WRITE});
        bookService.grantPermission("user2", book1,
            new BookService.Permission[]{BookService.Permission.READ});

        Book book2 = new Book(2L, "test");
        bookService.createBook(book2);
        bookService.grantPermission("user1", book2,
            new BookService.Permission[]{BookService.Permission.READ, BookService.Permission.WRITE});
    }

    @Test(expected = NotFoundException.class)
    public void testGrantPermissionAuthenticationRequired() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("admin", "pass1",
                Collections.singletonList(new SimpleGrantedAuthority("USER"))));

        bookService.grantPermission("user1", new Book(1L, "test"),
            new BookService.Permission[]{BookService.Permission.READ, BookService.Permission.WRITE});
    }

    @Test
    public void testFilterAdmin() {
        List<Book> books = bookService.findAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    public void testFilterUser1() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("user1", "pass1",
                Collections.singletonList(new SimpleGrantedAuthority("USER"))));

        List<Book> books = bookService.findAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    public void testFilterUser2() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("user2", "pass2",
                Collections.singletonList(new SimpleGrantedAuthority("USER"))));

        List<Book> books = bookService.findAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    public void testFilterUser3() {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("user3", "pass3",
                Collections.singletonList(new SimpleGrantedAuthority("USER"))));

        List<Book> books = bookService.findAllBooks();
        assertEquals(0, books.size());
    }

}