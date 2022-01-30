package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken, Email email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.email = email.getEmail();
        this.roles = roles;
    }

    // getters and setters
}