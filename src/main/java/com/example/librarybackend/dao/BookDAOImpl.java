package com.example.librarybackend.dao;

import com.example.librarybackend.dto.BookDTO;
import com.example.librarybackend.dto.Pagination;
import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    EntityManager entityManager;

    @Autowired
    public BookDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> getBooks() {
        return entityManager
                .createQuery("FROM Book", Book.class)
                .getResultList();
    }

    @Override
    public Book getBookById(long id) {
        try {
            Book book = entityManager
                        .createQuery("FROM Book WHERE id=:bookId", Book.class)
                        .setParameter("bookId", id)
                        .getSingleResult();
            return book;
        } catch (NoResultException ex) {
            throw new RuntimeException("Book id: " + id + " not found!");
        }
    }

    @Override
    public Book save(Book book) {
        if(book.getId() == 0)
            entityManager.persist(book);
        else
            throw new RuntimeException("Only for create new book");
        return book;
    }

    @Override
    public Book update(Book book) {
        Book theBook = getBookById(book.getId());
        if(theBook == null)
            throw new RuntimeException("This book not exists id: " + book.getId());
        entityManager.merge(book);
        return theBook;
    }

    @Override
    public void deleteBookById(long id) {
        Book theBook = getBookById(id);
        if(theBook == null)
            throw new RuntimeException("This book not exists");
        entityManager
                .createQuery("DELETE FROM Book WHERE id=:theId", Book.class)
                .setParameter("theId", id)
                .executeUpdate();
    }

    @Override
    public PaginationBooksDTO searchBooks(String title, String category, int pageNo, int pageSize) {
        String queryString = "SELECT b FROM Book b WHERE upper(b.title) LIKE :title";
        String queryCountString = "SELECT count (b) from Book b WHERE upper(b.title) LIKE :title";

        if(!category.isEmpty()) {
            queryString += " AND b.category=:category";
            queryCountString += " AND b.category=:category";
        }

        TypedQuery<Book> query = entityManager
                        .createQuery(queryString, Book.class)
                        .setParameter("title", "%" + title.toUpperCase() + "%")
                        .setFirstResult((pageNo) * pageSize)
                        .setMaxResults(pageSize);

        if(!category.isEmpty()) {
            query.setParameter("category", category);
        }

        List<Book> books = query.getResultList();


        TypedQuery<Long> countQuery = entityManager.createQuery(queryCountString, Long.class)
                .setParameter("title", "%" + title.toUpperCase() + "%");

        if(!category.isEmpty()) {
            countQuery.setParameter("category", category);
        }

        long totalOfBooks = countQuery.getSingleResult();

        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            bookDTOs.add(new BookDTO(book));
        }
        Pagination pagination = new Pagination(totalOfBooks, pageNo, (int)Math.ceil((double) totalOfBooks / pageSize));

        return new PaginationBooksDTO(bookDTOs, pagination);
    }
}
