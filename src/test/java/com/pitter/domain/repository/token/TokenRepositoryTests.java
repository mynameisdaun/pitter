package com.pitter.domain.repository.token;

import com.pitter.common.exception.UnIdentifiedRefreshTokenException;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.member.NickName;
import com.pitter.domain.entity.member.Role;
import com.pitter.domain.entity.token.RefreshToken;
import com.pitter.domain.entity.token.SocialLoginToken;
import com.pitter.domain.entity.token.SocialProvider;
import com.pitter.domain.entity.token.Token;
import com.pitter.domain.repository.member.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location=classpath:/application.properties,classpath:/application-oauth.properties")
@Transactional
public class TokenRepositoryTests {

    @Autowired private MemberRepository memberRepository;

    @Autowired private RefreshTokenRepository tokenRepository;

    @Test
    public void tdd() throws Exception {
        //given


        //when


        //then

    }

//    @Test
//    public void Member를_사용하여_토큰을_조회한다() throws Exception {
//        //given
//        Email email = new Email("tester@pitter.com");
//        Member member = Member.createMember(new NickName("테스터"), email,"oAuthPassword1!", Role.USER);
//        SocialLoginToken socialLoginToken = new SocialLoginToken("ACCESS_TOKEN",5000L,"REFRESH_TOKEN",5000L, SocialProvider.PITTER);
//        Token token = Token.generateToken(member, socialLoginToken);
//        memberRepository.save(member);
//        tokenRepository.saveAndFlush(token);
//
//        //when
//        Token findToken = tokenRepository.findByMember(member).orElseThrow(UnIdentifiedRefreshTokenException::new);
//
//        //then
//        assertEquals(token,findToken);
//    }
//
//    @Test
//    public void Email을_사용하여_토큰을_조회한다() throws Exception {
//        //given
//        Email email = new Email("tester@pitter.com");
//        Member member = Member.createMember(new NickName("테스터"), email,"oAuthPassword1!", Role.USER);
//        SocialLoginToken socialLoginToken = new SocialLoginToken("ACCESS_TOKEN",5000L,"REFRESH_TOKEN",5000L, SocialProvider.PITTER);
//        Token token = Token.generateToken(member, socialLoginToken);
//        memberRepository.save(member);
//        tokenRepository.saveAndFlush(token);
//
//        //when
//        RefreshToken findToken = tokenRepository.findByEmail(email)
//                .orElseThrow(UnIdentifiedRefreshTokenException::new);
//
//        //then
//        assertEquals(token,findToken);
//    }

}