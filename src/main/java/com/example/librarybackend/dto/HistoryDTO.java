package com.example.librarybackend.dto;

import com.example.librarybackend.dao.HistoryDAO;
import com.example.librarybackend.entity.History;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class HistoryDTO {
    String userEmail;
    String checkoutDate;
    String returnedDate;
    String title;
    String author;
    String description;
    String img;

    public HistoryDTO(History history) {
        setUserEmail(history.getUserEmail());
        setCheckoutDate(history.getCheckoutDate());
        setReturnedDate(history.getReturnedDate());
        setTitle(history.getTitle());
        setAuthor(history.getAuthor());
        setDescription(history.getDescription());
        setImg(history.getImg());
    }
}
