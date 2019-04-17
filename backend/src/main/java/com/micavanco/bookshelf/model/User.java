package com.micavanco.bookshelf.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user",
                cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                            CascadeType.DETACH, CascadeType.REFRESH})
    protected List<Book> books;

    User(){}

    public void add(Book book)
    {
        if(books == null)
            books = new ArrayList<>();

        books.add(book);

        book.setUser(this);
    }

}
