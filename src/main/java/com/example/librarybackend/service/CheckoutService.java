package com.example.librarybackend.service;

import com.example.librarybackend.dto.BookDTO;
import com.example.librarybackend.dto.CheckoutDTO;

import java.util.List;

public interface CheckoutService {
    CheckoutDTO findCheckoutByUserEmailAndBookId(String userEmail, long bookId) throws Exception;

    BookDTO checkoutBook(String userEmail, long bookId) throws Exception;

    public Boolean checkoutBookByUser(String userEmail, long bookId) throws Exception;

    public int currentLoansCount(String userEmail);
}
