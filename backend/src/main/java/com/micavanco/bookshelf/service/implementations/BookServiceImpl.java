package com.micavanco.bookshelf.service.implementations;

import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.BookRepository;
import com.micavanco.bookshelf.repository.UserRepository;
import com.micavanco.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("BookService")
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository)
    {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List getUserBooks(String username) {
        User user = userRepository.getByUsername(username);

        if(user == null)
            return null;

        return bookRepository.getUserBooks(user);
    }

    @Override
    public List getAll() {
        return bookRepository.getAll();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.getBooksByTitle(title);
    }

    @Override
    public boolean addBook(Book book, Long user_id) {
        User user = userRepository.getById(user_id);

        if(user == null)
            return false;

        user.add(book);
        userRepository.addUser(user);
        return true;
    }

    @Override
    public boolean deleteUserBookByTitle(Long user_id, String title) {
        User user = userRepository.getById(user_id);

        if(user == null)
            return false;

        bookRepository.deleteUserBookByTitle(user, title);
        return true;
    }

    @Override
    public List<Book> getUserBooksByTitle(String username, String title) {
        User user = userRepository.getByUsername(username);

        if(user == null)
            return null;

        return bookRepository.getUserBooksByTitle(user, title);
    }
}
