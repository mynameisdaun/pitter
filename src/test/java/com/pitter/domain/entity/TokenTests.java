package com.pitter.domain.entity;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.member.NickName;
import com.pitter.domain.entity.member.Role;
import com.pitter.domain.entity.token.InternalToken;
import com.pitter.domain.entity.token.SocialToken;
import com.pitter.domain.entity.token.SocialType;
import com.pitter.domain.entity.token.Token;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"classpath:/application.properties","classpath:/application-oauth.properties"})
@Transactional
public class TokenTests {
    private final static Logger logger = LoggerFactory.getLogger(TokenTests.class);
    private Member member;

    @Before
    public void setUp() {
        this.member = Member.createMember(new NickName("tester"), new Email("tester@pitter.com"), "TestPass@12!", Role.USER);
    }

    @Test
    public void 만료기간이_도래하지_않은_내부_API_엑세스_토큰을_검증한다() {
        //given
        InternalToken internalToken = new InternalToken(member);
        SocialToken socialToken =
                new SocialToken("accessToken",50L,"refreshToken",1000L, SocialType.KAKAO);
        Token token = Token.generateToken(member, socialToken);
        Date date = new Date();
        //when
        boolean isValidToken = token.isValidInternalAccessToken(date);
        //then
        assertThat(isValidToken).isEqualTo(true);
    }

    @Test
    public void 만료된_내부_API_엑세스_토큰을_검증한다() {
        //given
        SocialToken socialToken =
                new SocialToken("accessToken",50L,"refreshToken",1000L, SocialType.KAKAO);
        Token token = Token.generateToken(member, socialToken);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 30);
        Date date = calendar.getTime();
        //when
        boolean isValidToken = token.isValidInternalAccessToken(date);
        //then
        assertThat(isValidToken).isEqualTo(false);
    }

    @Test
    public void 유효한_내부_API_리프레쉬_토큰을_검증한다() {
        //given
        SocialToken socialToken =
                new SocialToken("accessToken",50L,"refreshToken",1000L, SocialType.KAKAO);
        Token token = Token.generateToken(member, socialToken);
        Date date = new Date();
        //when
        boolean isValidToken = token.isValidInternalRefreshToken(date);
        //then
        assertThat(isValidToken).isEqualTo(true);
    }

    @Test
    public void 만료된_내부_API_리프레쉬_토큰을_검증한다() {
        //given
        SocialToken socialToken =
                new SocialToken("accessToken",50L,"refreshToken",1000L, SocialType.KAKAO);
        Token token = Token.generateToken(member, socialToken);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 30);
        Date date = calendar.getTime();
        //when
        boolean isValidToken = token.isValidInternalRefreshToken(date);
        System.out.println(token.getInternalToken().getInternalAccessTokenExpireAt());
        System.out.println(token.getInternalToken().getInternalRefreshTokenExpireAt());
        System.out.println(date.toString());
        //then
        assertThat(isValidToken).isEqualTo(false);
    }

}