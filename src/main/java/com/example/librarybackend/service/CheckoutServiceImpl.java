package com.example.librarybackend.service;

import com.example.librarybackend.dao.BookDAO;
import com.example.librarybackend.dao.CheckoutDAO;
import com.example.librarybackend.dto.BookDTO;
import com.example.librarybackend.dto.CheckoutDTO;
import com.example.librarybackend.entity.Book;
import com.example.librarybackend.entity.Checkout;
import com.example.librarybackend.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CheckoutServiceImpl implements CheckoutService{
    CheckoutDAO checkoutDAO;

    BookDAO bookDAO;

    public CheckoutServiceImpl(CheckoutDAO checkoutDAO, BookDAO bookDAO) {
        this.checkoutDAO = checkoutDAO;
        this.bookDAO = bookDAO;
    }

    @Override
    public CheckoutDTO findCheckoutByUserEmailAndBookId(String userEmail, long bookId) throws Exception {
        Checkout checkout = checkoutDAO.findCheckoutByUserEmailAndBookId(userEmail, bookId);
        if(checkout == null)
            throw new Exception("Checkout not found");
        return new CheckoutDTO(checkout);
    }

    @Override
    public BookDTO checkoutBook(String userEmail, long bookId) throws Exception {
        Book book = bookDAO.getBookById(bookId);
        Checkout validateCheckout = checkoutDAO.findCheckoutByUserEmailAndBookId(userEmail, bookId);
        if(book == null || validateCheckout != null || book.getCopiesAvailable() <= 0)
            throw new Exception("Book already checked out");

        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookDAO.update(book);

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.getId()
        );

        checkoutDAO.save(checkout);
        return new BookDTO(book);
    }
}
