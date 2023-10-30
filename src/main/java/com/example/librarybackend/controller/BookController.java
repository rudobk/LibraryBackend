package com.example.librarybackend.controller;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dao.BookDAO;
import com.example.librarybackend.entity.Book;
import com.example.librarybackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books")
    public List<Book> getBooksPaginated(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "0") int size) {
        try {
            return bookService.findPaginated(page, size);
        }  catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @PostMapping(value = "books")
    public Book addBooks(@RequestBody Book book) {
        book.setId(0);
        try {
            return bookService.save(book);
        }  catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @GetMapping(value = "books/{id}")
    public Book getBookById(@PathVariable long id) {
        try {
            return bookService.findById(id);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @DeleteMapping(value = "books/{id}")
    public void deleteBookById(@PathVariable long id) {
        try {
            bookService.deleteById(id);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }
}
