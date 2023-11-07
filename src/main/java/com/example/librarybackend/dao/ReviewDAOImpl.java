package com.example.librarybackend.dao;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dto.*;
import com.example.librarybackend.entity.Book;
import com.example.librarybackend.entity.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDAOImpl implements ReviewDAO{

    EntityManager entityManager;

    @Autowired
    public ReviewDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Review save(Review review) {
        if(review.getId() == 0)
            entityManager.persist(review);
        else
            throw new RuntimeException("Only create new review");
        return review;
    }

    @Override
    public List<Review> getReviewsByBookId(long id) {
        return entityManager
                        .createQuery("FROM Review WHERE bookId=:bookId", Review.class)
                        .setParameter("bookId", id)
                        .getResultList();
    }

    @Override
    public PaginationReviewDTO searchReviews(int bookId, int pageNo, int pageSize) {
        String queryStr = "FROM Review";
        if(bookId != 0) {
            queryStr += " WHERE bookId=:bookId";
        }
        TypedQuery<Review> query = entityManager
                .createQuery("FROM Review WHERE bookId=:bookId", Review.class);

        if(bookId != 0) {
            query.setParameter("bookId", bookId);
        }
        if(pageSize > 0) {
            query.setMaxResults(pageSize);
            if(pageNo > 0) {
                query.setFirstResult((pageNo - 1) * pageSize);
            }
        }
        List<Review> reviews = query.getResultList();

        TypedQuery<Long> countQuery = entityManager
                .createQuery("SELECT COUNT(*) FROM Review WHERE bookId=:bookId", Long.class)
                .setParameter("bookId", bookId);
        long totalOfReviews = countQuery.getSingleResult();
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review: reviews) {
            reviewDTOs.add(new ReviewDTO(review));
        }

        int totalPages = (pageSize > 0) ? (int)Math.ceil((double) totalOfReviews / pageSize) : 1;

        Pagination pagination = new Pagination(totalOfReviews, pageNo, totalPages);
        return new PaginationReviewDTO(pagination, reviewDTOs);
    }
}
