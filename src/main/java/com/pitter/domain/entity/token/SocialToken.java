package com.pitter.domain.entity.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

import static com.pitter.utils.StringUtils.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class SocialToken {

    private String socialAccessToken;

    private LocalDateTime socialAccessTokenExpireAt;

    private String socialRefreshToken;

    private LocalDateTime socialRefreshTokenExpireAt;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    public SocialToken(String socialAccessToken, Long socialAccessTokenExpiresIn, String socialRefreshToken, Long socialRefreshTokenExpiresIn, SocialProvider socialProvider) {
        checkNullOrEmpty(socialAccessToken,"엑세스 토큰으로 빈 값이 올 수 없습니다.");
        checkNullOrEmpty(socialRefreshToken,"리프레쉬 토큰으로 빈 값이 올 수 없습니다.");
        LocalDateTime now = LocalDateTime.now();
        this.socialAccessToken = socialAccessToken;
        this.socialAccessTokenExpireAt = now.plusSeconds(socialAccessTokenExpiresIn);
        this.socialRefreshToken = socialRefreshToken;
        this.socialRefreshTokenExpireAt = now.plusSeconds(socialRefreshTokenExpiresIn);
        this.socialProvider = socialProvider;
    }

}
