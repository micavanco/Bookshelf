package com.micavanco.bookshelf.controller;


import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.BookRepository;
import com.micavanco.bookshelf.repository.UserRepository;
import com.micavanco.bookshelf.service.BookService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Book> addBook(@RequestBody String data)
    {
        JSONObject user_book = new JSONObject(data);
        Book book = createBookfromJSON(user_book);
        user_book = new JSONObject(data);
        boolean wasCreated;
        try {
            wasCreated = bookService.addBook(book, new Long(user_book.get("user_id").toString()), user_book.get("user_password").toString());
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return wasCreated ? new ResponseEntity<Book>(HttpStatus.CREATED):
                new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Book> updateBook(@RequestBody String data)
    {
        JSONObject user_book = new JSONObject(data);
        Book book = createBookfromJSON(user_book);
        user_book = new JSONObject(data);
        boolean wasUpdated;
        try {
            wasUpdated = bookService.updateBook(book, new Long(user_book.get("user_id").toString()), user_book.get("user_password").toString());
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return wasUpdated ? new ResponseEntity<Book>(HttpStatus.OK):
                new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getUserBooks(@RequestParam(value = "username")String username,
                                                   @RequestParam(value = "user_password")String user_password)
    {
        List<Book> books;
        try {
            books = bookService.getUserBooks(username, user_password);
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(books == null)
            return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);

        return books.size() > 0 ? new ResponseEntity<List<Book>>(books, HttpStatus.OK)
                : new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(value = "title")String title)
    {
        List<Book> books;
        try {
            books = bookService.searchBooks(title);
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(books == null)
            return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);


        return books.size() > 0 ? new ResponseEntity<List<Book>>(books, HttpStatus.OK)
                : new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/getBookDetails", method = RequestMethod.POST)
    public ResponseEntity<Book> getBookDetail(@RequestBody String url)
    {
        Book book;
        try {
            book = bookService.getBookDetails(url);
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(book == null)
            return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);


        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/user/title", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getUserBooksByTitle(
            @RequestParam(value = "username")String username,
            @RequestParam(value = "title")String title,
            @RequestParam(value = "user_password")String user_password)
    {
        List<Book> books;
        try {
            books = bookService.getUserBooksByTitle(username, title, user_password);
        }catch (Exception ex)
        {
            return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return books.size() > 0 ? new ResponseEntity<List<Book>>(books, HttpStatus.OK)
                : new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasRole('ADMIN')")
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

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteUserBook(@RequestParam(value = "user_id")Long user_id,
                                               @RequestParam(value = "title")String title,
                                               @RequestParam(value = "user_password")String user_password)
    {
        boolean wasDeleted;
        try {
            wasDeleted = bookService.deleteUserBookByTitle(user_id, title, user_password);
        }catch (Exception ex)
        {
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return wasDeleted ? new ResponseEntity<>(HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasRole('ADMIN')")
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

    private Book createBookfromJSON(JSONObject user_book)
    {
        user_book = user_book.getJSONObject("book");
        Book book = new Book();
        book.setTitle(user_book.get("title").toString());
        book.setAuthor(user_book.get("author").toString());
        book.setYear(new Integer(user_book.get("year").toString()));
        book.setPages(new Integer(user_book.get("pages").toString()));
        book.setLanguage(user_book.get("language").toString());
        book.setPublisher(user_book.get("publisher").toString());
        book.setCover(user_book.get("cover").toString());
        book.setPages_done(new Integer(user_book.get("pages_done").toString()));
        return book;
    }

}
