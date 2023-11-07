package com.example.librarybackend.controller;

import com.example.librarybackend.dao.ReviewDAO;
import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.dto.PaginationReviewDTO;
import com.example.librarybackend.dto.ReviewDTO;
import com.example.librarybackend.entity.Review;
import com.example.librarybackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/reviews/{id}")
    public List<ReviewDTO> getReviewsByBookId(@PathVariable long id) {
        return reviewService.getReviewsByBookId(id);
    }

    @PostMapping(value = "/reviews/secured")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void postReview(@RequestBody ReviewDTO reviewDTO) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        reviewDTO.setUserEmail(userEmail);
        reviewService.postReview(userEmail, reviewDTO);
    }

    @GetMapping(value = "/reviews/secured/user/book")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Boolean checkIfUserReviewed(@RequestParam Long bookId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return reviewService.checkIfUserReviewed(userEmail, bookId);
    }

    @GetMapping(value = "/reviews")
    public ResponseEntity<PaginationReviewDTO> getReviewPaginated(@RequestParam(required = false, defaultValue = "") int bookId,
                                                                 @RequestParam(required = false, defaultValue = "0") int page,
                                                                 @RequestParam(required = false, defaultValue = "0") int size) {
        return ResponseEntity.ok(reviewService.searchReviews(bookId, page, size));
    }
}
