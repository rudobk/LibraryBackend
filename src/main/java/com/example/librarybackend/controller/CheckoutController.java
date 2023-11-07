package com.example.librarybackend.controller;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dto.BookDTO;
import com.example.librarybackend.service.BookService;
import com.example.librarybackend.service.CheckoutService;
import com.example.librarybackend.service.CheckoutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/books")
public class CheckoutController {

    private CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PutMapping(value = "/secured/checkout")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookDTO checkoutBook(@RequestParam long bookId) throws Exception {
        String UserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return checkoutService.checkoutBook(UserEmail, bookId);
    }

    @GetMapping(value = "/secured/ischeckedout/byuser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Boolean checkedOutByUser(@RequestParam long bookId) throws Exception {
        String UserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return checkoutService.checkoutBookByUser(UserEmail, bookId);
    }

    @GetMapping(value = "/secured/currentloans/count")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Integer currentLoansCount() throws Exception {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return checkoutService.currentLoansCount(userEmail);
    }
}
