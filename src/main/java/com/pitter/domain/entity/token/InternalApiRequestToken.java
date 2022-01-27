package com.pitter.domain.entity.token;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.utils.JwtUtils;
import com.pitter.utils.PropertiesUtils;
import io.jsonwebtoken.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Date;

import static com.pitter.utils.PropertiesUtils.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString @Setter @Slf4j
public class InternalApiRequestToken {

    @Transient private Email email;
    @Transient private TokenType tokenType;
    @Transient private Claims claims;
    private String token;
    private LocalDateTime tokenExpireAt;

    public InternalApiRequestToken(Email email, TokenType tokenType, String token) {
        this.email = email;
        this.token = token;
        this.tokenType = tokenType;
        this.tokenExpireAt = setTokenExpireDate(tokenType);
        System.out.println("getJwtSecretKey: "+getJwtSecretKey());
        System.out.println("token: "+token);
        this.claims = Jwts.parser().setSigningKey(getJwtSecretKey()).parseClaimsJws(token).getBody();
        System.out.println("debug please");
    }

//    public boolean isValidToken(Date date) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(getJwtSecretKey())
//                    .parseClaimsJws(token).getBody();
//
//            return claims.getExpiration()
//                    .after(date)
//                    && claims.getSubject()
//                    .equals(email.getEmail());
//        } catch (SignatureException ex) {
//            log.error("토큰의 서명이 올바르지 않습니다.");
//        } catch (MalformedJwtException ex) {
//            log.error("토큰의 형식이 올바르지 않습니다.");
//        } catch (ExpiredJwtException ex) {
//            log.error("토큰의 유효기간이 만료되었습니다.");
//        } catch (UnsupportedJwtException ex) {
//            log.error("지원하지 않는 토큰 방식입니다.");
//        } catch (IllegalArgumentException ex) {
//            log.error("토큰의 내용은 빈 값이 올 수 없습니다.");
//        }
//        return false;
//    }

    public boolean isExpired(Date date) {
        return claims.getExpiration()
                .after(date);
    }

    public boolean isValidSubject() {
        System.out.println("[========== we are debugging 2 ==========]");
            return claims.getSubject()
                    .equals(email.getEmail());
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
