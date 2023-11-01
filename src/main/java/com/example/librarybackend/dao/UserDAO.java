package com.example.librarybackend.dao;

import com.example.librarybackend.entity.User;

public interface UserDAO {
    User findByEmail(String email);
    User save(User user);
}
