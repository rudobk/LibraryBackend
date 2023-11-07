package com.example.librarybackend.service;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dao.ReviewDAO;
import com.example.librarybackend.dto.PaginationReviewDTO;
import com.example.librarybackend.dto.ReviewDTO;
import com.example.librarybackend.entity.Review;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    ReviewDAO reviewDAO;

    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    @Override
    public List<ReviewDTO> getReviewsByBookId(long id) {
        List<Review> reviews = reviewDAO.getReviewsByBookId(id);
        List<ReviewDTO> reviewDTOs = new ArrayList<>();

        for (Review review: reviews) { reviewDTOs.add(new ReviewDTO(review));}

        return reviewDTOs;
    }

    @Override
    public ReviewDTO findByUserEmailAndBookId(String userEmail, long bookId) {
        Review review = reviewDAO.findByUserEmailAndBookId(userEmail, bookId);
        if(review == null)
            return null;
        return new ReviewDTO(review);
    }

    @Override
    public Boolean checkIfUserReviewed(String userEmail, long bookId) {
        ReviewDTO reviewDTO = findByUserEmailAndBookId(userEmail, bookId);
        return reviewDTO != null;
    }

    @Override
    public void postReview(String userEmail, ReviewDTO reviewDTO) {
        ReviewDTO validateReview = findByUserEmailAndBookId(userEmail, reviewDTO.getBookId());
        if(validateReview != null) {
            throw new CustomException("Review already created");
        }

        Review review = new Review();
        review.setBookId(reviewDTO.getBookId());
        review.setUserEmail(reviewDTO.getUserEmail());
        if(reviewDTO.getReviewDescription() != null)
            review.setReviewDescription(reviewDTO.getReviewDescription());
        review.setRating(reviewDTO.getRating());
        review.setDate(Date.valueOf(LocalDate.now()));

        reviewDAO.save(review);
    }

    @Override
    public PaginationReviewDTO searchReviews(int bookId, int pageNo, int pageSize) {
        return reviewDAO.searchReviews(bookId, pageNo, pageSize);
    }
}
