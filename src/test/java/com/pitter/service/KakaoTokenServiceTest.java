package com.pitter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.pitter.controller.dto.KakaoToken;
import com.pitter.controller.dto.KakaoUserInfo;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.NickName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location=classpath:/application.properties,classpath:/application-oauth.properties")
@WebFluxTest
@AutoConfigureMockMvc
public class KakaoTokenServiceTest {
    private final static Logger logger = LoggerFactory.getLogger(KakaoTokenServiceTest.class);

    @Autowired private KakaoTokenService kakaoTokenService;

    @Autowired private MockMvc mockMvc;

    @Autowired WebApplicationContext context;

    Map<String, String> requestBody;

    private ObjectMapper objectMapper;
    private final String authDomain = "https://kauth.kakao.com";
    private String accessTokenUri = "/oauth/token";
    private final String apiDomain = "https://kapi.kakao.com";
    private String userInfoUri = "/v2/user/me";

    private KakaoToken kakaoToken;
    private KakaoUserInfo kakaoUserInfo;

    @Before
    public void setUp() throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        requestBody = Maps.newHashMap();

        kakaoToken = new KakaoToken();
        kakaoToken.setToken_type("bearer");
        kakaoToken.setAccess_token("f2f3Vd32d0xGYxR7z8zRvN2nskFKoBhrwOu9xygwfe55dAAF-vxOF12s");
        kakaoToken.setExpires_in(21599);
        kakaoToken.setRefresh_token("12v_cascaca12gAEij22YCgYD_skvjdiejvowkkdsks-vxOF1w");
        kakaoToken.setRefresh_token_expires_in(5183999);
        kakaoToken.setScope("account_email profile_image profile_nickname");/

        kakaoUserInfo = new KakaoUserInfo();
        kakaoUserInfo.setEmail(new Email("tester@pitter.com"));
        kakaoUserInfo.setNickname(new NickName("pitterTester"));
        kakaoUserInfo.setProfileImageUrl("http://k.kakaocdn.net/dn/fjsodfijewf/sfjwofijwsefiowjfosdjifsdfsf/img_640x640.jpg");
    }

    @Test//
    public void OAuthTokenTest() throws JsonProcessingException {
        //given
        String auth_code = "fake_auth_code1234";
        String expectedUrl = authDomain + kakaoTokenService.getAccessTokenRequestUrl(auth_code);

        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        KakaoToken kakaoToken = kakaoTokenService.getAccessToken(auth_code);
        logger.info("{}",kakaoToken);
        assertThat(kakaoToken).isNotNull();
    }


}