package com.example.librarybackend.service;

import com.example.librarybackend.dao.ReviewDAO;
import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.dto.PaginationReviewDTO;
import com.example.librarybackend.dto.ReviewDTO;
import com.example.librarybackend.entity.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getReviewsByBookId(long id);

    ReviewDTO save(ReviewDTO reviewDTO);

    PaginationReviewDTO searchReviews(int bookId, int pageNo, int pageSize);
}
