package com.example.librarybackend.dto;

import lombok.Data;

@Data
public class Pagination {
    private long totalRecords;
    private long currentPage;
    private long totalPages;

    public Pagination(long totalRecords, long currentPage, long totalPages) {
        this.totalRecords = totalRecords;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
