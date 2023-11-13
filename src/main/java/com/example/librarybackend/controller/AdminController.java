package com.example.librarybackend.controller;

import com.example.librarybackend.request.AddBookRequest;
import com.example.librarybackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/secured/add/book")
    @PreAuthorize("hasRole('ADMIN')")
    public void addBook(@RequestBody AddBookRequest addBookRequest) {
        adminService.addBook(addBookRequest);
    }

    @PutMapping(value = "/secured/increase/book/quantity")
    @PreAuthorize("hasRole('ADMIN')")
    public void increaseBookQuantity(@RequestParam(required = true) Long bookId) {
        adminService.increaseBookQuantity(bookId);
    }

    @PutMapping(value = "/secured/decrease/book/quantity")
    @PreAuthorize("hasRole('ADMIN')")
    public void decreaseBookQuantity(@RequestParam(required = true) Long bookId) {
        adminService.decreaseBookQuantity(bookId);
    }

    @DeleteMapping(value = "/secured/book")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBookById(@RequestParam(required = true) Long bookId) {
        adminService.deleteBook(bookId);
    }
}
