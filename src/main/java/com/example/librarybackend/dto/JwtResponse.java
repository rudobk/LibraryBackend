package com.example.librarybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String token;

    private final String type = "Bearer";

    private long id;

    private String email;

    private String role;
}
