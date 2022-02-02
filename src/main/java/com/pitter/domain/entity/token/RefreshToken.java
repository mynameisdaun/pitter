package com.pitter.domain.entity.token;

import com.pitter.common.exception.TokenRefreshException;
import com.pitter.domain.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.pitter.common.utils.DateUtils.now;

@Entity @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {


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

    public static RefreshToken createRefreshToken(Member member, Long period) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Math.toIntExact(period));
        return new RefreshToken(
                member,
                UUID.randomUUID().toString(),
                calendar.getTime()
        );
    }

    public RefreshToken verifyExpiration() {
        if(expiryDate.compareTo(now()) < 0) {
            throw new TokenRefreshException("리프레쉬 토큰을 갱신할 수 없습니다. 다시 로그인 해 주세요.");
        }
        this.token = UUID.randomUUID().toString();
        return this;
    }

}
