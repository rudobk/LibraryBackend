package com.example.librarybackend.service;

import com.example.librarybackend.dao.ReviewDAO;
import com.example.librarybackend.dto.PaginationReviewDTO;
import com.example.librarybackend.dto.ReviewDTO;
import com.example.librarybackend.entity.Review;
import org.springframework.stereotype.Service;

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
    public ReviewDTO save(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewDescription(reviewDTO.getReviewDescription());
        review.setRating(reviewDTO.getRating());
        review.setUserEmail(reviewDTO.getUserEmail());
        review.setBookId(reviewDTO.getBookId());
        review.setId(reviewDTO.getId());
        review.setDate(reviewDTO.getDate());

        Review savedReview = reviewDAO.save(review);
        return new ReviewDTO(savedReview);
    }

    @Override
    public PaginationReviewDTO searchReviews(int bookId, int pageNo, int pageSize) {
        return reviewDAO.searchReviews(bookId, pageNo, pageSize);
    }
}
