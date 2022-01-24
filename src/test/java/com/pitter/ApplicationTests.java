package com.pitter;

import com.pitter.domain.entity.Token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void dd() throws Exception {
//                   .setExpiration(new Date(now.getTime() + tokenPeriod))
//        return new Token(member, accessToken, now.plusNanos(accessTokenPeriod), refreshToken, now.plusNanos(refreshTokenPeriod), now);

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        Date dateNow = new Date();

        System.out.println(dateNow.getTime()+1000L);
        System.out.println(System.currentTimeMillis()+1000L);
        System.out.println(Timestamp.valueOf(localDateTimeNow.plusNanos(1000L)).getTime());
        System.out.println(localDateTimeNow);
        System.out.println(localDateTimeNow.plusNanos(100000000000L));

    }

}
