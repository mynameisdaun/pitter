package com.pitter.controller.api.token;

import com.pitter.controller.dto.TokenValidateRequest;
import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.service.MemberService;
import com.pitter.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ValidateTokenController {

    private final TokenService tokenService;
    private final MemberService memberService;

    @PostMapping("/oauth/token")
    public ResponseEntity<TokenValidateResponse> validateToken(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody TokenValidateRequest tokenValidateRequest) {
        Email email = tokenValidateRequest.getEmail();
        String token = tokenValidateRequest.getToken();
        if(tokenValidateRequest.getTokenType() == TokenType.REFRESH_TOKEN) {
            tokenService.validateRefreshToken(email, token);
        }
        tokenService.validateAccessToken(email, token);
        return ResponseEntity.of();
    }

}
