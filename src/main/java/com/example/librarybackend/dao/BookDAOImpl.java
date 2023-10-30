package com.example.librarybackend.dao;

import com.example.librarybackend.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
            throw new RuntimeException("Just use update for id");
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
    public List<Book> getBooksPaginated(int pageNo, int pageSize) {
        return entityManager
                        .createQuery("FROM Book", Book.class)
                        .setFirstResult((pageNo) * pageSize)
                        .setMaxResults(pageSize)
                        .getResultList();
    }
}
