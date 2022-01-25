package com.pitter.domain.entity.token;

import com.pitter.domain.entity.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class InternalAccessToken {

    private String jwtSecretKey="hiscsfsfddsfsdf12e1dc";
    private Long accessTokenPeriod=100L;
    private Long refreshTokenPeriod=200L;

//    @Autowired
//    @Transient @Value("${com.pitter.jwtSecretKey}")
//    private String jwtSecretKey;
//
//    @Autowired
//    @Transient @Value("${com.pitter.jwtAccessTokenPeriod}")
//    private Long accessTokenPeriod;
//
//    @Autowired
//    @Transient @Value("${com.pitter.jwtRefreshTokenPeriod}")
//    private Long refreshTokenPeriod;

//    @Autowired
//    public void setJwtSecretKey( @Value("${com.pitter.jwtSecretKey}") String jwtSecretKey) {
//        this.jwtSecretKey = jwtSecretKey;
//    }
//    @Autowired
//    public void setAccessTokenPeriod( @Value("${com.pitter.jwtAccessTokenPeriod}") Long accessTokenPeriod) {
//        this.accessTokenPeriod = accessTokenPeriod;
//    }
//    @Autowired
//    public void setRefreshTokenPeriod( @Value("${com.pitter.jwtRefreshTokenPeriod}") Long refreshTokenPeriod) {
//        this.refreshTokenPeriod = refreshTokenPeriod;
//    }

    @Transient private String internalAccessToken;

    @Transient private LocalDateTime internalAccessTokenExpireAt;

    private String internalRefreshToken;

    private LocalDateTime internalRefreshTokenExpireAt;

    public InternalAccessToken(Member member) {
        this.internalAccessToken = tokenBuilder(member, accessTokenPeriod);
        this.internalRefreshToken = tokenBuilder(member, refreshTokenPeriod);
        LocalDateTime now = LocalDateTime.now();
        this.internalAccessTokenExpireAt = now.plusSeconds(accessTokenPeriod/1000);
        this.internalRefreshTokenExpireAt = now.plusSeconds(refreshTokenPeriod/1000);
    }

    public boolean isValidAccessToken() {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(internalAccessToken);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isValidRefreshToken() {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(internalRefreshToken);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String tokenBuilder(Member member, Long tokenPeriod) {
        System.out.println("debug!");
        System.out.println(tokenPeriod);
        Claims claims = Jwts.claims()
                .setSubject(member.getEmail().getEmail());
        claims.put("role", member.getRole().getRole());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }
}
