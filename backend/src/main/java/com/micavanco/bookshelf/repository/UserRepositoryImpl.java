package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        entityManager.remove(user);
    }

    @Override
    @Transactional
    public User getByUsername(String username) {

        List<User> users = entityManager.createQuery("Select u FROM User u where u.username=:username").setParameter("username",username).getResultList();

        if(users.size() == 0)
            return null;

        return users.get(0);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return entityManager.createQuery("select u from users u").getResultList();
    }

    @Override
    @Transactional
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        User user = getByUsername(username);

        if(user == null)
            return;

        entityManager.remove(user);
    }
}
