package com.example.librarybackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "user_email")
    String userEmail;

    @Column(name = "checkout_date")
    String checkoutDate;

    @Column(name = "returned_date")
    String returnedDate;

    @Column(name = "title")
    String title;

    @Column(name = "author")
    String author;

    @Column(name = "description")
    String description;

    @Column(name = "img")
    String img;

    public History() {

    }

    public History(String userEmail, String checkoutDate, String returnedDate, String title, String author, String description, String img) {
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnedDate = returnedDate;
        this.title = title;
        this.author = author;
        this.description = description;
        this.img = img;
    }
}
