package com.example.librarybackend.dto;

import com.example.librarybackend.entity.Review;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {
    private long id;
    private String userEmail;
    private Date date;
    private float rating;
    private int bookId;
    private String reviewDescription;

    public ReviewDTO(Review review) {
        setId(review.getId());
        setUserEmail(review.getUserEmail());
        setDate(review.getDate());
        setRating(review.getRating());
        setBookId(review.getBookId());
        setReviewDescription(review.getReviewDescription());
    }
}
