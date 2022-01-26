package com.pitter.domain.entity.token;

import com.pitter.domain.entity.BaseEntity;
import com.pitter.domain.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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
    private InternalToken internalToken;

    @Embedded
    private SocialToken socialToken;

    private Token(Member member, InternalToken internalToken, SocialToken socialToken) {
        this.member = member;
        this.internalToken = internalToken;
        this.socialToken = socialToken;
    }

    public static Token generateToken(Member member, SocialToken socialToken) {
        return new Token(member, new InternalToken(member), socialToken);
    }
}
