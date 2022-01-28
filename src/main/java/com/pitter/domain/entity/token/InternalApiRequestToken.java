package com.pitter.domain.entity.token;

import com.pitter.common.exception.InvalidSubjectEmailException;
import com.pitter.domain.entity.member.Email;
import io.jsonwebtoken.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Date;

import static com.pitter.common.utils.PropertiesUtils.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString @Setter @Slf4j
public class InternalApiRequestToken {

    @Transient private Email email;
    @Transient private TokenType tokenType;
    private String token;
    private LocalDateTime tokenExpireAt;

    public InternalApiRequestToken(Email email, TokenType tokenType, String token) {
        this.email = email;
        this.token = token;
        this.tokenType = tokenType;
        this.tokenExpireAt = setTokenExpireDate(tokenType);
    }

    public boolean isValid(Date date) throws JwtException {
        Claims claims = Jwts.parser().setSigningKey(getJwtSecretKey().getBytes()).parseClaimsJws(token).getBody();
        if(!claims.getSubject().equals(email.getEmail())) {
            throw new InvalidSubjectEmailException("요청자와 토큰의 소유자가 일치하지 않습니다.");
        }
        return !claims.getExpiration().after(date);
    }

    private LocalDateTime setTokenExpireDate(TokenType tokenType) {
        LocalDateTime now = LocalDateTime.now();
        if(tokenType==TokenType.ACCESS_TOKEN) {
            return now.plusSeconds(getJwtAccessTokenPeriod());
        }
        return now.plusSeconds(getJwtRefreshTokenPeriod());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InternalApiRequestToken that = (InternalApiRequestToken) o;

        if (!token.equals(that.token)) return false;
        return tokenExpireAt.equals(that.tokenExpireAt);
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + tokenExpireAt.hashCode();
        return result;
    }
}
