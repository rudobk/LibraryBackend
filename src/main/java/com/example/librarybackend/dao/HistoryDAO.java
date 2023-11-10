package com.example.librarybackend.dao;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dto.PaginationHistoriesDTO;
import com.example.librarybackend.entity.History;

public interface HistoryDAO {

    History findById(long id);

    History save(History history);

    PaginationHistoriesDTO findHistoryByEmail(String userEmail, int pageNo, int pageSize);
}
