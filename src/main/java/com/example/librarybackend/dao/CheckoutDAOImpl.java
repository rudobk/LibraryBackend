package com.example.librarybackend.dao;

import com.example.librarybackend.entity.Checkout;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CheckoutDAOImpl implements CheckoutDAO{
    EntityManager entityManager;

    @Autowired
    public CheckoutDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Checkout findCheckoutByUserEmailAndBookId(String userEmail, long bookId) {
        return entityManager
                .createQuery("FROM Checkout WHERE Checkout.userEmail=:userEmail AND Checkout.bookId=:bookId", Checkout.class)
                .setParameter("userEmail", userEmail)
                .setParameter("bookId", bookId)
                .getSingleResult();
    }

    @Override
    public Checkout save(Checkout checkout) {
        entityManager.persist(checkout);

        return checkout;
    }
}
