package com.example.librarybackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationBooksDTO {
    private List<BookDTO> books;

    private Pagination pagination;

    public PaginationBooksDTO(List<BookDTO> books, Pagination pagination) {
        this.books = books;
        this.pagination = pagination;
    }
}
