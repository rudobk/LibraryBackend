package com.example.librarybackend.service;

import com.example.librarybackend.dto.BookDTO;
import com.example.librarybackend.dto.CheckoutDTO;
import com.example.librarybackend.dto.PaginationHistoriesDTO;
import com.example.librarybackend.dto.ShelfCurrentLoansDTO;

import java.util.List;

public interface CheckoutService {
    CheckoutDTO findCheckoutByUserEmailAndBookId(String userEmail, long bookId) throws Exception;

    BookDTO checkoutBook(String userEmail, long bookId) throws Exception;

    void returnBook(String userEmail, long bookId) throws Exception;

    void renewBook(String userEmail, long bookId) throws Exception;

    Boolean checkoutBookByUser(String userEmail, long bookId) throws Exception;

    int currentLoansCount(String userEmail);

    List<ShelfCurrentLoansDTO> currentLoans(String userEmail) throws Exception;

    PaginationHistoriesDTO getHistory(String userEmail, int pageNo, int pageSize) throws Exception;
}
