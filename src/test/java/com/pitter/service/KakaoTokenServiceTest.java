package com.pitter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitter.controller.dto.KakaoToken;
import com.pitter.controller.dto.KakaoUserInfo;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.NickName;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
//@SpringBootTest(properties = "spring.config.location=classpath:/application.properties,classpath:/application-oauth.properties")
//@Transactional
@RestClientTest(value=KakaoTokenService.class)
public class KakaoTokenServiceTest {
    private final static Logger logger = LoggerFactory.getLogger(KakaoTokenServiceTest.class);

    @Autowired private KakaoTokenService kakaoTokenService;

    @Autowired private MockRestServiceServer mockServer;

    private ObjectMapper objectMapper;
    private final String authDomain = "https://kauth.kakao.com";
    private String accessTokenUri = "/oauth/token";
    private final String apiDomain = "https://kapi.kakao.com";
    private String userInfoUri = "/v2/user/me";

    private KakaoToken kakaoToken;
    private KakaoUserInfo kakaoUserInfo;

    @Before
    public void setUp() throws JsonProcessingException {
        kakaoToken = new KakaoToken();
        kakaoToken.setToken_type("bearer");
        kakaoToken.setAccess_token("f2f3Vd32d0xGYxR7z8zRvN2nskFKoBhrwOu9xygwfe55dAAF-vxOF12s");
        kakaoToken.setExpires_in(21599);
        kakaoToken.setRefresh_token("12v_cascaca12gAEij22YCgYD_skvjdiejvowkkdsks-vxOF1w");
        kakaoToken.setRefresh_token_expires_in(5183999);
        kakaoToken.setScope("account_email profile_image profile_nickname");

        kakaoUserInfo = new KakaoUserInfo();
        kakaoUserInfo.setEmail(new Email("tester@pitter.com"));
        kakaoUserInfo.setNickname(new NickName("pitterTester"));
        kakaoUserInfo.setProfileImageUrl("http://k.kakaocdn.net/dn/fjsodfijewf/sfjwofijwsefiowjfosdjifsdfsf/img_640x640.jpg");
    }

    @Test
    public void OAuthTokenTest() throws JsonProcessingException {
        //given
        String auth_code = "fake_auth_code1234";
        String expectedUrl = authDomain + kakaoTokenService.getAccessTokenRequestUrl(auth_code);
        mockServer
                .expect(requestTo(expectedUrl))
                .andRespond(withSuccess(objectMapper.writeValueAsString(kakaoToken), MediaType.APPLICATION_JSON));

        KakaoToken kakaoToken = kakaoTokenService.getAccessToken(auth_code);
        logger.info("{}",kakaoToken);
        assertThat(kakaoToken).isNotNull();
    }

}