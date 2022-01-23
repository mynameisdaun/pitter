package com.pitter.domain.entity;

import com.pitter.domain.repository.MemberRepository;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
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

    @Test
    public void 토큰을_생성한다() throws Exception {
        //given
        Member member = Member.createMember("tester", "tester@pitter.com", "TestPass@12!", Role.USER);
        memberRepository.save(member);
        entityManager.flush();

        Token token = Token.generateToken(member);
        //when

        //then
        assertThat(token).isNotNull();
    }

}