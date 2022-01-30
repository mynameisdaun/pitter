package com.pitter.domain.entity.token;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static com.pitter.common.utils.DateUtils.now;

@Entity @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Value("{com.pitter.refreshTokenPeriod}") @Transient
    private Long refreshTokenPeriod;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name="email")
    private Member member;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Date expiryDate;

    private RefreshToken(Member member, String token, Date expiryDate) {
        this.member = member;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public RefreshToken createRefreshToken(Member member) {
        return new RefreshToken(member, UUID.randomUUID().toString(), now().toInstant().plusMillis(refreshTokenPeriod). );
    }

}
