package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.Book;
import com.micavanco.bookshelf.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("bookRepository")
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional
    public List getUserBooks(User user) {
        Long user_id = user.getId();
        List<Book> books;
        try {
            books = entityManager.createQuery("SELECT b from Book b where b.user.id=:user_id").setParameter("user_id",user_id).getResultList();
        }catch (Exception ex)
        {
            return null;
        }

        return books;
    }

    @Override
    @Transactional
    public Book getBookByTitle(String title) {
        List<Book> books = entityManager.createQuery("from Book where title=:title").setParameter("title",title).getResultList();

        if(books.size() == 0)
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

    @Override
    @Transactional
    public void deleteUserBookByTitle(User user, String title) {
        List<Book> books = entityManager.createQuery("SELECT b from Book b where b.user.id=:user_id and b.title=:title")
                .setParameter("user_id", user.getId())
                .setParameter("title", title)
                .getResultList();

        if(books.size() == 0)
            return;

        entityManager.remove(books.get(0));
    }

    @Override
    @Transactional
    public Book getUserBookByTitle(User user, String title) {
        List<Book> books = entityManager.createQuery("select b from Book b where b.user.id=:user_id and b.title=:title")
                .setParameter("user_id", user.getId())
                .setParameter("title", title)
                .getResultList();

        if(books.size() == 0)
            return null;

        return books.get(0);
    }
}
