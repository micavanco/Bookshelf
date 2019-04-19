package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.User;

import java.util.List;

public interface UserRepository {
    void addUser(User user);
    void removeUser(User user);
    User getByUsername(String username);
    List getAll();
    User getById(Long id);
    void deleteUserByUsername(String username);
}
