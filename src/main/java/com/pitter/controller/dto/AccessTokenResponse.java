package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor @Data
public class AccessTokenResponse {
    private String accessToken;
    private String type = "Bearer";
    private String refreshToken;
    private Email email;
    private String nickName;
    private List<Role> roles;

    public AccessTokenResponse(String accessToken, String refreshToken, Email email, String nickName, List<Role> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
        this.nickName = nickName;
        this.roles = roles;
    }
}
