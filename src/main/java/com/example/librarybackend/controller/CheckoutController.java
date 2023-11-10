package com.example.librarybackend.controller;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dto.BookDTO;
import com.example.librarybackend.dto.PaginationHistoriesDTO;
import com.example.librarybackend.dto.ShelfCurrentLoansDTO;
import com.example.librarybackend.service.BookService;
import com.example.librarybackend.service.CheckoutService;
import com.example.librarybackend.service.CheckoutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping(value = "/secured/return")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void returnBook(@RequestParam long bookId) throws Exception {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        checkoutService.returnBook(userEmail, bookId);
    }

    @PutMapping(value = "/secured/renew")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void renewBook(@RequestParam long bookId) throws Exception {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        checkoutService.renewBook(userEmail, bookId);
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

    @GetMapping(value = "/secured/currentloans")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ShelfCurrentLoansDTO> currentLoans() throws Exception {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return checkoutService.currentLoans(userEmail);
    }

    @GetMapping(value = "/secured/history")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public PaginationHistoriesDTO getHistory(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "0") int size) throws Exception {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return checkoutService.getHistory(userEmail, page, size);
    }
}
