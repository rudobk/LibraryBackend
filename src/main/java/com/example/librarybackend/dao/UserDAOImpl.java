package com.example.librarybackend.dao;

import com.example.librarybackend.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByEmail(String email) {
        return entityManager.createQuery("FROM User WHERE userEmail=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public User save(User user) {
        if(findByEmail(user.getUserEmail()) != null)
            throw new RuntimeException("This email already exists: " + user.getUserEmail());
        return entityManager.merge(user);
    }
}
