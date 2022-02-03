package com.pitter.controller.api.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.pitter.controller.dto.KakaoToken;
import com.pitter.controller.dto.KakaoUserInfo;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.NickName;
import com.pitter.service.KakaoTokenService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"classpath:/application.properties","classpath:/application-oauth.properties"})
@AutoConfigureMockMvc
@Transactional
public class SocialSignInControllerTest {

    @MockBean private KakaoTokenService kakaoTokenService;

    @Autowired private MockMvc mockMvc;

    @Autowired private WebApplicationContext context;

    Map<String, String> requestBody;

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        requestBody = Maps.newHashMap();
    }

    @Test
    public void 유효한_엑세스토큰을_검증한다 () throws Exception {
        //given
        KakaoToken kakaoToken = new KakaoToken();
        kakaoToken.setToken_type("bearer");
        kakaoToken.setAccess_token("f2f3Vd32d0xGYxR7z8zRvN2nskFKoBhrwOu9xygwfe55dAAF-vxOF12s");
        kakaoToken.setExpires_in(21599);
        kakaoToken.setRefresh_token("12v_cascaca12gAEij22YCgYD_skvjdiejvowkkdsks-vxOF1w");
        kakaoToken.setRefresh_token_expires_in(5183999);
        kakaoToken.setScope("account_email profile_image profile_nickname");

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo();
        kakaoUserInfo.setEmail(new Email("tester@pitter.com"));
        kakaoUserInfo.setNickname(new NickName("pitterTester"));
        kakaoUserInfo.setProfileImageUrl("http://k.kakaocdn.net/dn/fjsodfijewf/sfjwofijwsefiowjfosdjifsdfsf/img_640x640.jpg");

        when(kakaoTokenService.getAccessToken("auth_code")).thenReturn(kakaoToken);
        when(kakaoTokenService.getKakaoUserInfo(kakaoToken.getAccess_token())).thenReturn(kakaoUserInfo);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/sign_in/kakao?code=fakeCode123123qdqd89"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
//then
//        assertThat(response.getContentAsString(), containsString(SUCCESS.getCode()));
//        assertThat(response.getContentAsString(), containsString(SUCCESS.getMessage()));
    }
}