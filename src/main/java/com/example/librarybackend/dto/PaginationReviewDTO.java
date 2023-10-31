package com.example.librarybackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationReviewDTO {
    List<ReviewDTO> reviews;

    private Pagination pagination;

    public PaginationReviewDTO(Pagination pagination, List<ReviewDTO> reviews) {
        this.pagination = pagination;
        this.reviews = reviews;
    }
}
