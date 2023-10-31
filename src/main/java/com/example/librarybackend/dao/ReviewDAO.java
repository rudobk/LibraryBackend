package com.example.librarybackend.dao;

import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.dto.PaginationReviewDTO;
import com.example.librarybackend.entity.Review;

import java.util.List;

public interface ReviewDAO {
    Review save(Review review);
    List<Review> getReviewsByBookId(long id);

    PaginationReviewDTO searchReviews(int bookId, int pageNo, int pageSize);
}
