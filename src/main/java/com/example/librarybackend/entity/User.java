package com.example.librarybackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.java.Log;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "role")
    private String role;

    public User(String userEmail, String password, String role) {
        this.userEmail = userEmail;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }

    public User() {

    }
}
