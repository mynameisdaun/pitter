package com.pitter.controller.api.login;

import com.pitter.controller.dto.TokenValidateRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class ValidateTokenController {

    @PostMapping("/oauth2/token/validate")
    public void validateToken(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody TokenValidateRequestDto tokenValidateRequestDto) {

    }
}
