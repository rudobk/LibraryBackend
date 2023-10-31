package com.example.librarybackend.dto;

import com.example.librarybackend.entity.Book;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BookDTO {
    private long id;

    private String title;

    private String author;

    private String description;

    private int copies;

    private int copiesAvailable;

    private String category;

    private String img;
    public BookDTO(Book book) {
        setId(book.getId());
        setTitle(book.getTitle());
        setAuthor(book.getAuthor());
        setDescription(book.getDescription());
        setCopies(book.getCopies());
        setCopiesAvailable(book.getCopiesAvailable());
        setCategory(book.getCategory());
        setImg(book.getImg());
    }
}
