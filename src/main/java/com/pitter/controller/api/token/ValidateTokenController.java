package com.pitter.controller.api.token;

import com.pitter.common.utils.JwtUtils;
import com.pitter.controller.dto.TokenRefreshRequest;
import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.service.RefreshTokenService;
import com.pitter.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ValidateTokenController {
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;

    @PostMapping("/signIn")
    public ResponseEntity<TokenValidateResponse> validateToken(HttpServletRequest req, HttpServletResponse res,
                                                               @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        //TokenValidateResponse response = tokenService.validate(tokenRefreshRequest.toTokenEntity());
        //return ResponseEntity.ok().body(response);
        return null;
    }

}
