package com.example.librarybackend.dao;

import com.example.librarybackend.entity.Checkout;

import java.util.List;

public interface CheckoutDAO {

    Checkout findCheckoutByUserEmailAndBookId(String userEmail, long bookId);

    Checkout save(Checkout checkout);

    Checkout update(Checkout checkout);
    List<Checkout> getCheckoutsByUserEmail(String userEmail);

    Checkout getCheckoutById(long id);

    void deleteCheckoutById(long id);
}
