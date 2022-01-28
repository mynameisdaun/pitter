package com.pitter.domain.entity.token;

import com.pitter.controller.dto.TokenCode;
import com.pitter.domain.entity.BaseEntity;
import com.pitter.domain.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import static com.pitter.controller.dto.TokenCode.*;
import static com.pitter.common.utils.DateUtils.*;
import static com.pitter.common.utils.JwtUtils.*;

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
        //TODO jwt 토큰 생성에 날짜를 주입하는 것에 대해서 생각해봐야 한다..
        Date now = new Date();
        System.out.println("from Date:"+now.getTime());
        return new Token(
                member,
                new InternalApiRequestToken(member.getEmail(),TokenType.REFRESH_TOKEN, jwtTokenBuilder(member.getEmail(), TokenType.REFRESH_TOKEN, now)),
                socialLoginToken
        );
    }
}
