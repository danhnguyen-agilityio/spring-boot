package com.agility.spring.security.acl;

import java.util.List;

public interface BookService {

    enum Permission {
        READ, WRITE
    }

    void createBook(Book book);

    Book findBookById(long id);

    List<Book> findAllBooks();

    public void updateBook(Book book);

    public void grantPermission(String principal, Book book, Permission[] permissions);
}
