package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.Book;

import java.util.List;

public interface BookRepository {
    List getUserBooks(String username);
    List getAll();
    Book getBookByTitle(String title);
    void addBook(Book book);
}
