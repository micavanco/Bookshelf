package com.micavanco.bookshelf.service;

import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;

import java.util.List;

public interface BookService {
    List getUserBooks(String username, String user_password);
    List getAll();
    List getBooksByTitle(String title);
    boolean addBook(Book book, Long user_id, String user_password);
    boolean deleteUserBookByTitle(Long user_id, String title, String user_password);
    List getUserBooksByTitle(String username, String title, String user_password);
    List searchBooks(String title);
    Book getBookDetails(String url);
}
