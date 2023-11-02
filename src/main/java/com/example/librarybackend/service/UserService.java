package com.example.librarybackend.service;

import com.example.librarybackend.dao.UserDAO;
import com.example.librarybackend.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.librarybackend.entity.User;

@Service
public class UserService implements UserDetailsService {

    UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.findByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found: " + username);
        return new CustomUserDetails(user);
    }
}