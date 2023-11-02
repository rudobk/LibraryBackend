package com.example.librarybackend.controller;

import com.example.librarybackend.dao.UserDAO;
import com.example.librarybackend.dto.JwtResponse;
import com.example.librarybackend.dto.LoginRequest;
import com.example.librarybackend.dto.SignupRequest;
import com.example.librarybackend.entity.CustomUserDetails;
import com.example.librarybackend.entity.User;
import com.example.librarybackend.jwt.JwtTokenProvider;
import com.example.librarybackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthenticationManager authenticationManager;

    JwtTokenProvider jwtTokenProvider;

    UserDAO userDAO;

    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }



    @PostMapping("/signin")
    public ResponseEntity<?> signinAuthenticator(@Valid @RequestBody LoginRequest loginRequest) {
        try
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(user);

            String role = user.getAuthorities().toString();
            return ResponseEntity.ok(new JwtResponse(jwt, user.getUser().getId(), user.getUsername(), role));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Username/password incorrect");
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupAuthenticator(@Valid @RequestBody SignupRequest signupRequest) {
        System.out.println(signupRequest);
        if(userDAO.findByEmail(signupRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        String role = "USER";

        if(signupRequest.getRole() != null) {
            switch (signupRequest.getRole()) {
                case "ADMIN":
                    role = "ADMIN";
                default:
                    role = "USER";
            }
        }
        User user = new User(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()), role);

        userDAO.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

}
