package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.User;

import java.util.List;

public interface UserRepository {
    User addUser(User user);
    User removeUser(User user);
    User getById(Integer id);
    List<User> getAll();
}
