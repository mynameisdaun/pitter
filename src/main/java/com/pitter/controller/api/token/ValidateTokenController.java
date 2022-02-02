package com.pitter.controller.api.token;

import com.pitter.common.utils.JwtUtils;
import com.pitter.controller.dto.TokenRefreshRequest;
import com.pitter.controller.dto.TokenRefreshResponse;
import com.pitter.domain.entity.token.RefreshToken;
import com.pitter.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.pitter.common.utils.DateUtils.now;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ValidateTokenController {
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenRefreshResponse> validateToken(HttpServletRequest req, HttpServletResponse res,
                                                              @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(tokenRefreshRequest.getRefreshToken());
        RefreshToken updatedRefreshToken = refreshTokenService.verifyExpiration(refreshToken);
        String accessToken = jwtUtils.jwtTokenBuilder(updatedRefreshToken.getMember().getEmail(), now());
        TokenRefreshResponse tokenRefreshResponse = new TokenRefreshResponse(accessToken, refreshToken.getToken());
        return ResponseEntity
                .ok()
                .body(tokenRefreshResponse);
    }
}
