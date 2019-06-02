package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;

import java.util.List;

public interface BookRepository {
    List getUserBooks(User user);
    List getAll();
    List getBooksByTitle(String title);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteUserBookByTitle(User user, String title);
    List getUserBooksByTitle(User user, String title);
}
