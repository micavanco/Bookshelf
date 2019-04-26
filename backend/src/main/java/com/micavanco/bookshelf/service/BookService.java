package com.micavanco.bookshelf.service;

import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;

import java.util.List;

public interface BookService {
    List getUserBooks(String username);
    List getAll();
    List getBooksByTitle(String title);
    boolean addBook(Book book, Long user_id);
    boolean deleteUserBookByTitle(Long user_id, String title);
    List getUserBooksByTitle(String username, String title);
}
