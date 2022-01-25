package com.pitter.domain.entity.token;

import com.pitter.domain.entity.BaseEntity;
import com.pitter.domain.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class Token extends BaseEntity implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "email")
    private Member member;

    @Embedded @NotBlank
    private InternalAccessToken internalAccessToken;

    @Embedded @NotBlank
    private SocialAccessToken socialAccessToken;

    private Token(Member member, InternalAccessToken internalAccessToken, SocialAccessToken socialAccessToken) {
        this.member = member;
        this.internalAccessToken = internalAccessToken;
        this.socialAccessToken = socialAccessToken;
    }

    public static Token generateToken(Member member, SocialAccessToken socialAccessToken) {
        return new Token(member, new InternalAccessToken(member), socialAccessToken);
    }

    public boolean isValidInternalAccessToken() {
        return this.internalAccessToken.isValidAccessToken();
    }

    public boolean isValidInternalRefreshToken() {
        return this.internalAccessToken.isValidRefreshToken();
    }
}
