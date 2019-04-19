package com.micavanco.bookshelf.controller;


import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.BookRepository;
import com.micavanco.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public BookController(BookRepository bookRepository, UserRepository userRepository)
    {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Book> addBook(@RequestParam(value = "title")String title,
                                        @RequestParam(value = "author")String author,
                                        @RequestParam(value = "year")Integer year,
                                        @RequestParam(value = "pages")Integer pages,
                                        @RequestParam(value = "language")String language,
                                        @RequestParam(value = "publisher")String publisher,
                                        @RequestParam(value = "cover")String cover,
                                        @RequestParam(value = "user_id")Long user_id)
    {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        book.setPages(pages);
        book.setLanguage(language);
        book.setPublisher(publisher);
        book.setCover(cover);
        User user;
        try {
            user = userRepository.getById(user_id);
            user.add(book);
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            userRepository.addUser(user);
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            bookRepository.addBook(book);
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getUserBooks(@RequestParam(value = "username")String username)
    {
        List<Book> books;
        try {
            books = bookRepository.getUserBooks(username);
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

}
