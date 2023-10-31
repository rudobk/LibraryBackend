package com.example.librarybackend.service;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dao.BookDAO;
import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    BookDAO bookDAO;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public List<Book> findAll() {
        try {
            return bookDAO.getBooks();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public Book findById(long id) {
        try {
            return bookDAO.getBookById(id);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public Book save(Book book) {
        try {
            return bookDAO.save(book);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public Book update(Book book) {
        try {
            return bookDAO.update(book);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            bookDAO.deleteBookById(id);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public PaginationBooksDTO searchBooks(String title, String category , int pageNo, int pageSize) {
        try {
            return bookDAO.searchBooks(title, category, pageNo, pageSize);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }
}
