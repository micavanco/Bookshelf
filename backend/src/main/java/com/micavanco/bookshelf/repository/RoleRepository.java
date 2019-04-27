package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.Role;

public interface RoleRepository {
    void add(Role role);
    void delete(Role role);
    Role get(Long user_id);
}
