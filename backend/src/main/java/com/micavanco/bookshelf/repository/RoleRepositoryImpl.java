package com.micavanco.bookshelf.repository;

import com.micavanco.bookshelf.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void add(Role role) {
        entityManager.merge(role);
    }

    @Override
    @Transactional
    public void delete(Role role) {
        entityManager.remove(role);
    }

    @Override
    @Transactional
    public Role get(Long user_id) {

        List<Role> roles = entityManager.createQuery("select r from Role r where r.user_id=:user_id")
                .setParameter("user_id",user_id).getResultList();
        if(roles.size() == 0)
            return null;

        return roles.get(0);
    }
}
