package com.example.librarybackend.service;

import com.example.librarybackend.request.AddBookRequest;

public interface AdminService {

    void addBook(AddBookRequest addBookRequest);

    void increaseBookQuantity(Long bookId);

    void decreaseBookQuantity(Long bookId);

    void deleteBook(Long bookId);
}
