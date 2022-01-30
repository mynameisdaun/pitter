package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.common.exception.TokenTypeException;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static com.pitter.domain.entity.token.TokenType.*;

@Data
public class TokenRefreshRequest {
    @NotBlank private String refreshToken;


}
