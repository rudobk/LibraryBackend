package com.example.librarybackend.controller;

import com.example.librarybackend.dao.ReviewDAO;
import com.example.librarybackend.dto.PaginationBooksDTO;
import com.example.librarybackend.dto.PaginationReviewDTO;
import com.example.librarybackend.dto.ReviewDTO;
import com.example.librarybackend.entity.Review;
import com.example.librarybackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    List<ReviewDTO> getReviewsByBookId(@PathVariable long id) {
        return reviewService.getReviewsByBookId(id);
    }

    @PostMapping(value = "/reviews/{id}")
    ReviewDTO save(@RequestBody ReviewDTO reviewDTO, @PathVariable long id) {
        reviewDTO.setId(id);
        return reviewService.save(reviewDTO);
    }

    @GetMapping(value = "/reviews")
    public ResponseEntity<PaginationReviewDTO> getReviewPaginated(@RequestParam(required = false, defaultValue = "") int bookId,
                                                                 @RequestParam(required = false, defaultValue = "0") int page,
                                                                 @RequestParam(required = false, defaultValue = "0") int size) {
        return ResponseEntity.ok(reviewService.searchReviews(bookId, page, size));
    }
}
