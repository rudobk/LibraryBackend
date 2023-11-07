package com.example.librarybackend.dao;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.entity.Checkout;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
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
        try {
            return entityManager
                    .createQuery("FROM Checkout WHERE userEmail=:userEmail AND bookId=:bookId", Checkout.class)
                    .setParameter("userEmail", userEmail)
                    .setParameter("bookId", bookId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Checkout save(Checkout checkout) {
        entityManager.persist(checkout);

        return checkout;
    }

    @Override
    public List<Checkout> getCheckoutsByUserEmail(String userEmail) {
        return entityManager
                .createQuery("FROM Checkout WHERE userEmail=:userEmail", Checkout.class)
                .setParameter("userEmail", userEmail)
                .getResultList();
    }
}
