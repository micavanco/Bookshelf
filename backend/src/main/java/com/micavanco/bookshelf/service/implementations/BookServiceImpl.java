package com.micavanco.bookshelf.service.implementations;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.BookRepository;
import com.micavanco.bookshelf.repository.UserRepository;
import com.micavanco.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
@Service("BookService")
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List getUserBooks(String username, String user_password) {
        User user = userRepository.getByUsername(username);

        if(user == null || !user_password.equals(user.getPassword()))
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
    public boolean addBook(Book book, Long user_id, String user_password) {
        User user = userRepository.getById(user_id);
        if(user == null || !user_password.equals(user.getPassword()))
            return false;

        user.addBook(book);
        userRepository.addUser(user);
        return true;
    }

    @Override
    public boolean deleteUserBookByTitle(Long user_id, String title, String user_password) {
        User user = userRepository.getById(user_id);

        if(user == null || !passwordEncoder.matches(user_password, user.getPassword()))
            return false;

        bookRepository.deleteUserBookByTitle(user, title);
        return true;
    }

    @Override
    public List<Book> getUserBooksByTitle(String username, String title, String user_password) {
        User user = userRepository.getByUsername(username);

        if(user == null || !passwordEncoder.matches(user_password, user.getPassword()))
            return null;

        return bookRepository.getUserBooksByTitle(user, title);
    }

    @Override
    public List searchBooks(String title) {
        List<Book> books = new ArrayList<>();
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            String searchUrl = "https://www.amazon.com/s?k="+URLEncoder.encode(title, "UTF-8")+"&i=stripbooks-intl-ship&ref=nb_sb_noss";
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[@class='s-result-list sg-row']/div");
            if(items.isEmpty())
                return null;


            for(HtmlElement e : items)
            {
                String []book = e.asText().split("\n");
                HtmlAnchor itemAnchor = ((HtmlAnchor) e.getFirstByXPath(".//a[@class='a-link-normal a-text-normal']"));
                books.add(new Book(book[0], book[1], 1995, 324, "null", "https://www.amazon.com/"+itemAnchor.getHrefAttribute(), "null", 0));
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        return books;
    }
}
