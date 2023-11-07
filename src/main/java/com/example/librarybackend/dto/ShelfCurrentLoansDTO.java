package com.example.librarybackend.dto;

import com.example.librarybackend.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShelfCurrentLoansDTO {
    BookDTO book;
    long daysLeft;
}
