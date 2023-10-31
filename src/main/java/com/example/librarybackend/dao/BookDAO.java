package com.example.librarybackend.dao;

import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.entity.Book;

import java.util.List;

public interface BookDAO {

    List<Book> getBooks();
    Book getBookById(long id);
    Book save(Book book);
    Book update(Book book);
    void deleteBookById(long id);

    PaginationBooksDTO searchBooks(String title, String category, int pageNo, int pageSize);
}
