package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;

import java.util.List;

public interface BookRepository {
    List getUserBooks(User user);
    List getAll();
    Book getBookByTitle(String title);
    void addBook(Book book);
    void deleteUserBookByTitle(User user, String title);
    Book getUserBookByTitle(User user, String title);
}
