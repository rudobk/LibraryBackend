package com.example.librarybackend.service;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dao.BookDAO;
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
        return bookDAO.getBooks();
    }

    @Override
    public Book findById(long id) {
        return bookDAO.getBookById(id);
    }

    @Override
    public Book save(Book book) {
        return bookDAO.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookDAO.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDAO.deleteBookById(id);
    }

    @Override
    public List<Book> findPaginated(int pageNo, int pageSize) {
        System.out.println("Here pageNo: " + pageNo + " pageSize: " + pageSize);
        if(pageNo <= 0 && pageSize <= 0)
            return findAll();
        return bookDAO.getBooksPaginated(pageNo, pageSize);
    }
}
