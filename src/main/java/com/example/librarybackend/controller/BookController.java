package com.example.librarybackend.controller;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.entity.Book;
import com.example.librarybackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books")
    public ResponseEntity<PaginationBooksDTO> getBooksPaginated(@RequestParam(required = false, defaultValue = "") String title,
                                                                @RequestParam(required = false, defaultValue = "") String category,
                                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                                @RequestParam(required = false, defaultValue = "0") int size) {
            return ResponseEntity.ok(bookService.searchBooks(title, category, page, size));
    }


    @GetMapping(value = "books/{id}")
    public Book getBookById(@PathVariable long id) {
        try {
            return bookService.findById(id);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }
}
