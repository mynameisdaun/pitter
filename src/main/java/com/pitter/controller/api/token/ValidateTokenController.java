package com.pitter.controller.api.token;

import com.pitter.controller.dto.TokenValidateRequest;
import com.pitter.controller.dto.TokenValidateResponse;
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

    @PostMapping("/oauth/token")
    public ResponseEntity<TokenValidateResponse> validateToken(HttpServletRequest req, HttpServletResponse res,
                                                               @Valid @RequestBody TokenValidateRequest tokenValidateRequest) {
        TokenValidateResponse response = tokenService.validate(tokenValidateRequest.toTokenEntity());
        return ResponseEntity.ok().body(response);
    }

}
