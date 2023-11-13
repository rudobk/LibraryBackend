package com.example.librarybackend.service;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dao.BookDAO;
import com.example.librarybackend.entity.Book;
import com.example.librarybackend.request.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    BookDAO bookDAO;

    @Autowired
    public AdminServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void addBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookDAO.save(book);
    }

    @Override
    public void increaseBookQuantity(Long bookId) {
        Book validateBook = bookDAO.getBookById(bookId);

        if(validateBook == null)
            throw new CustomException("Book not found");

        validateBook.setCopies(validateBook.getCopies() + 1);
        validateBook.setCopiesAvailable(validateBook.getCopiesAvailable() + 1);

        bookDAO.update(validateBook);
    }

    @Override
    public void decreaseBookQuantity(Long bookId) {
        try {
            Book validateBook = bookDAO.getBookById(bookId);

            if(validateBook == null)
                throw new CustomException("Book not found");

            if(validateBook.getCopiesAvailable() <= 0 || validateBook.getCopies() <= 0)
                throw new CustomException("Invalid action");

            validateBook.setCopies(validateBook.getCopies() - 1);
            validateBook.setCopiesAvailable(validateBook.getCopiesAvailable() - 1);

            bookDAO.update(validateBook);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    public void deleteBook(Long bookId) {
        try
        {
            Book validateBook = bookDAO.getBookById(bookId);

            if(validateBook == null)
                throw new CustomException("Book not found");

            bookDAO.deleteBookById(bookId);
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }
}
