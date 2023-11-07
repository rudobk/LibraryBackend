package com.example.librarybackend.dto;

import com.example.librarybackend.entity.Review;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {
    private String userEmail;
    private Date date;
    private float rating;
    private int bookId;
    private String reviewDescription;

    public ReviewDTO(Review review) {
        setUserEmail(review.getUserEmail());
        setDate(review.getDate());
        setRating(review.getRating());
        setBookId(review.getBookId());
        setReviewDescription(review.getReviewDescription());
    }

    public ReviewDTO(float rating, int bookId, String reviewDescription) {
        this.rating = rating;
        this.bookId = bookId;
        this.reviewDescription = reviewDescription;
    }
}
