package com.example.librarybackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationHistoriesDTO {
    List<HistoryDTO> histories;
    Pagination pagination;

    public PaginationHistoriesDTO(List<HistoryDTO> histories, Pagination pagination) {
        this.histories = histories;
        this.pagination = pagination;
    }
}
