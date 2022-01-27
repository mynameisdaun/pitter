package com.pitter.domain.entity.token;

import com.pitter.controller.dto.TokenCode;
import com.pitter.domain.entity.BaseEntity;
import com.pitter.domain.entity.member.Member;
import com.pitter.utils.DateUtils;
import com.pitter.utils.JwtUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.pitter.controller.dto.TokenCode.*;
import static com.pitter.utils.DateUtils.*;
import static com.pitter.utils.JwtUtils.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class Token extends BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "email")
    private Member member;

    @Embedded
    private InternalApiRequestToken internalApiRequestToken;

    @Embedded
    private SocialLoginToken socialLoginToken;

    private Token(Member member, InternalApiRequestToken internalApiRequestToken, SocialLoginToken socialLoginToken) {
        this.member = member;
        this.internalApiRequestToken = internalApiRequestToken;
        this.socialLoginToken = socialLoginToken;
    }

    public static Token generateToken(Member member, SocialLoginToken socialLoginToken) {
        return new Token(member, new InternalApiRequestToken(member.getEmail(),TokenType.REFRESH_TOKEN, jwtTokenBuilder(member.getEmail(), TokenType.REFRESH_TOKEN)), socialLoginToken);
    }

    public TokenCode isValidRefreshToken(InternalApiRequestToken internalApiRequestToken) {
        if(this.internalApiRequestToken.getEmail() != internalApiRequestToken.getEmail()) {
            return INVALID_SIGNATURE;
        }
        if(this.internalApiRequestToken.getTokenExpireAt().isBefore(LocalDateTime.now())
             && internalApiRequestToken.isExpired(now())) {
            return EXPIRED_REFRESH_TOKEN;
        }
        return SUCCESS;
    }
}
