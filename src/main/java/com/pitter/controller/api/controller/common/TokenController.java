package com.pitter.controller.api.controller.common;

import com.pitter.config.auth.TokenService;
import com.pitter.config.auth.dto.Token;
import com.pitter.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/token/expired")
    public String auth() {
        throw new RuntimeException();
    }

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token =request.getHeader("Refresh");

        if(token!=null&&tokenService.verifyToken(token)){
            String email = tokenService.getEmail(token);
            Token newToken = tokenService.generateToken(email, Role.USER);

            response.addHeader("Auth", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "HAPPY NEW TOKEN";
        }

        throw new RuntimeException();
    }
}
