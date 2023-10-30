package com.example.librarybackend.service;

import com.example.librarybackend.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(long id);

    Book save(Book book);

    Book update(Book book);

    void deleteById(long id);

    List<Book> findPaginated(int pageNo, int pageSize);
}
