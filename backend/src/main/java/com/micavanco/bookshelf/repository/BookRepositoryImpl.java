package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookRepositoryImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List getUserBooks(String username) {

        List<User> users;
        try {
            users = entityManager.createQuery("SELECT u from User u where u.username=:username").setParameter("username",username).getResultList();
        }catch (Exception ex)
        {
            return null;
        }

        if(users == null)
            return null;

        Long user_id = users.get(0).getId();
        List<Book> books;
        try {
            books = entityManager.createQuery("SELECT b from Book b where b.user_id=:user_id").setParameter("user_id",user_id).getResultList();
        }catch (Exception ex)
        {
            return null;
        }

        return books;
    }

    @Override
    @Transactional
    public Book getBookByTitle(String title) {
        List<Book> books;
        try {
            books = entityManager.createQuery("SELECT b from Book b where b.title=:title").setParameter("title",title).getResultList();
        }catch (Exception ex)
        {
            return null;
        }

        if(books == null)
            return null;

        return books.get(0);
    }

    @Override
    @Transactional
    public List getAll() {
        List<Book> books;
        try {
            books = entityManager.createQuery("SELECT b from Book b").getResultList();
        }catch (Exception ex)
        {
            return null;
        }
        return books;
    }

    @Override
    @Transactional
    public void addBook(Book book) {
        try {
            entityManager.merge(book);
        }catch (Exception ex)
        {

        }
    }
}
