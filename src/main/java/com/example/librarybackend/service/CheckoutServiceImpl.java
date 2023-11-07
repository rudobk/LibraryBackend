package com.example.librarybackend.service;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dao.BookDAO;
import com.example.librarybackend.dao.CheckoutDAO;
import com.example.librarybackend.dto.BookDTO;
import com.example.librarybackend.dto.CheckoutDTO;
import com.example.librarybackend.dto.ShelfCurrentLoansDTO;
import com.example.librarybackend.entity.Book;
import com.example.librarybackend.entity.Checkout;
import com.example.librarybackend.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
            throw new CustomException("Book already checked out");

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

    public Boolean checkoutBookByUser(String userEmail, long bookId) throws Exception {
        Checkout checkout = checkoutDAO.findCheckoutByUserEmailAndBookId(userEmail, bookId);
        return checkout != null;
    }

    @Override
    public int currentLoansCount(String userEmail) {
        List<Checkout> checkouts = checkoutDAO.getCheckoutsByUserEmail(userEmail);
        return checkouts.size();
    }

    @Override
    public List<ShelfCurrentLoansDTO> currentLoans(String userEmail) throws Exception {
        List<ShelfCurrentLoansDTO> shelfCurrentLoansDTOs = new ArrayList<>();

        List<Checkout> checkouts = checkoutDAO.getCheckoutsByUserEmail(userEmail);

        List<Long> bookIds = new ArrayList<>();
        for (Checkout checkout: checkouts) {
            bookIds.add(checkout.getBookId());
        }

        List<Book> books = bookDAO.getBooksByIds(bookIds);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book:books) {
            Optional<Checkout> checkout = checkouts.stream().filter(c -> c.getBookId() == book.getId()).findFirst();
            if(checkout.isPresent()) {
                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long different_In_Time = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

                shelfCurrentLoansDTOs.add(new ShelfCurrentLoansDTO(
                        new BookDTO(book),
                        different_In_Time
                ));
            }
        }

        return shelfCurrentLoansDTOs;
    }
}
