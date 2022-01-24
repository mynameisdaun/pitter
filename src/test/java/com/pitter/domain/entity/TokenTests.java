package com.pitter.domain.entity;

import com.pitter.domain.repository.MemberRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"classpath:/application.properties","classpath:/application-oauth.properties"})
@Transactional
public class TokenTests {

    private final static Logger logger = LoggerFactory.getLogger(TokenTests.class);

    @Autowired private  MemberRepository memberRepository;
    @Autowired private EntityManager entityManager;
    private Member member;

    @Before
    public void setUp() {
        this.member = Member.createMember("tester", "tester@pitter.com", "TestPass@12!", Role.USER);
    }

    @Test
    public void 토큰을_생성한다() throws Exception {
        //given
        Token token = Token.generateToken(member);
        //when

        //then
        assertThat(token).isNotNull();
    }

    @Test
    public void 유효한_토큰을_검증한다() {
        //given
        Long expirationPeriod = 1000L * 60 * 6;
        String token = Token.tokenBuilder(member, expirationPeriod);

        //when
        boolean verifyAccessToken = Token.verifyToken(token);
        //then
        assertThat(verifyAccessToken).isTrue();
    }

    @Test
    public void 만료된_토큰을_검증한다() {
        //given
        Long expirationPeriod = -1000L * 60 * 6;
        String token = Token.tokenBuilder(member, expirationPeriod);

        //when
        boolean verifyAccessToken = Token.verifyToken(token);
        //then
        assertThat(verifyAccessToken).isFalse();
    }

}