package com.micavanco.bookshelf.service.implementations;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<Book> books = getUserBooksByTitle(user.getUsername(), book.getTitle(), user_password);

        if(books != null && books.get(0).getTitle().trim().equals(book.getTitle().trim()))
            return false;

        user.addBook(book);
        userRepository.addUser(user);
        return true;
    }

    @Override
    public boolean updateBook(Book book, Long user_id, String user_password) {
        User user = userRepository.getById(user_id);
        if(user == null || !user_password.equals(user.getPassword()))
            return false;

        book.setUser(user);
        bookRepository.updateBook(book);
        return true;
    }

    @Override
    public boolean deleteUserBookByTitle(Long user_id, String title, String user_password) {
        User user = userRepository.getById(user_id);

        if(user == null || !user_password.equals(user.getPassword()))
            return false;

        bookRepository.deleteUserBookByTitle(user, title);
        return true;
    }

    @Override
    public List<Book> getUserBooksByTitle(String username, String title, String user_password) {
        User user = userRepository.getByUsername(username);

        if(user == null || !user_password.equals(user.getPassword()))
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

            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[@class='s-result-list s-search-results sg-row']/div");

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

    @Override
    public Book getBookDetails(String url) {
        Book book = new Book();
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            HtmlPage page = client.getPage(url);

            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[@class='content']/ul/li");
            HtmlElement items2 = page.getFirstByXPath("//a[@class='a-link-normal contributorNameID']");
            HtmlElement items22 = page.getFirstByXPath("//a[@class='a-link-normal']");
            HtmlElement items3 = page.getFirstByXPath("//h1[@id='title']");
            HtmlElement items4 = page.getFirstByXPath("//div[@id='booksImageBlock_feature_div']");

            if(items.isEmpty())
                return null;

            Map<String, String> data = new HashMap<>();
            for(HtmlElement e : items)
                if(e.asText().contains(":"))
                {
                    String[]s = e.asText().split(":");
                    data.put(s[0], s[1]);
                }


            book.setPages_done(0);
            if(data.get("Paperback") != null)
                book.setPages(new Integer(data.get("Paperback").trim().split(" ")[0]));
            else if(data.get("Hardcover") != null)
                book.setPages(new Integer(data.get("Hardcover").trim().split(" ")[0]));
            else if(data.get("Print Length") != null)
                book.setPages(new Integer(data.get("Print Length").trim().split(" ")[0]));
            else
                book.setPages(new Integer(data.get("Spiral-bound").trim().split(" ")[0]));
            String[] pub = data.get("Publisher").split("\\(");
            book.setPublisher(pub[0].trim());
            String[] date = pub[1].substring(0, pub[1].length()-1).split(",");
            if(date.length > 1)
                book.setYear(new Integer(date[1].trim()));
            else
                book.setYear(new Integer(date[0].trim()));
            book.setLanguage(data.get("Language").trim());

            if(items2 != null)
                book.setAuthor(items2.asText());
            else
                book.setAuthor(items22.asText());

            book.setTitle(items3.asText());

            List<HtmlElement> images = items4.getHtmlElementsByTagName("img").stream()
                    .filter(e -> e.hasAttribute("id"))
                    .filter(e -> e.getId().equals("imgBlkFront") || e.getId().equals("main-image"))
                    .collect(Collectors.toList());

            book.setCover(images.get(0).getAttribute("data-a-dynamic-image").split("\"")[3]);

        }catch(Exception e){
            e.printStackTrace();
        }

        return book;
    }
}
