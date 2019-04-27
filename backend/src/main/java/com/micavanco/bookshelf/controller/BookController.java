package com.micavanco.bookshelf.controller;


import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.BookRepository;
import com.micavanco.bookshelf.repository.UserRepository;
import com.micavanco.bookshelf.service.BookService;
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

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService)
    {
        this.bookService = bookService;
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
        boolean wasCreated;
        try {
            wasCreated = bookService.addBook(book, user_id);
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return wasCreated ? new ResponseEntity<Book>(HttpStatus.CREATED):
                new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getUserBooks(@RequestParam(value = "username")String username)
    {
        List<Book> books;
        try {
            books = bookService.getUserBooks(username);
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return books.size() > 0 ? new ResponseEntity<List<Book>>(books, HttpStatus.OK)
                : new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/title", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getUserBooksByTitle(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "title")String title)
    {
        List<Book> books;
        try {
            books = bookService.getUserBooksByTitle(username, title);
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return books.size() > 0 ? new ResponseEntity<List<Book>>(books, HttpStatus.OK)
                : new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam(value = "title")String title)
    {
        List<Book> books;
        try {
            books = bookService.getBooksByTitle(title);
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return books != null ? new ResponseEntity<List<Book>>(books, HttpStatus.OK)
                : new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteUserBook(@RequestParam(value = "user_id")Long user_id,
                                               @RequestParam(value = "title")String title)
    {
        boolean wasDeleted;
        try {
            wasDeleted = bookService.deleteUserBookByTitle(user_id, title);
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return wasDeleted ? new ResponseEntity<>(HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks()
    {
        List<Book> books;
        try {
            books = bookService.getAll();
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return books.size() > 0 ? new ResponseEntity<List<Book>>(books, HttpStatus.OK)
                : new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
    }

}