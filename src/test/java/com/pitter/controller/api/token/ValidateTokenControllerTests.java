package com.pitter.controller.api.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.domain.repository.member.MemberRepository;
import com.pitter.domain.repository.token.RefreshTokenRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"classpath:/application.properties","classpath:/application-oauth.properties"})
@AutoConfigureMockMvc
@Transactional
public class ValidateTokenControllerTests {

    @Autowired ValidateTokenController validateTokenController;

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired WebApplicationContext context;

    @Autowired private RefreshTokenRepository tokenRepository;

    @Autowired private MemberRepository memberRepository;

    private Member member;
    private Email email;
    private TokenType tokenType;
    private String token;
    Map<String, String> requestBody;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        requestBody = Maps.newHashMap();
        email = new Email("tester@pitter.com");
    }

//    @Test
//    public void ?????????_??????????????????_???????????? () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", token);
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(SUCCESS.getCode()));
//        assertThat(response.getContentAsString(), containsString(SUCCESS.getMessage()));
//    }
//
//    @Test
//    public void ?????????_??????????????????_???????????? () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, oneMonthBefore());
//
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", token);
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(EXPIRED_ACCESS_TOKEN.getCode()));
//        assertThat(response.getContentAsString(), containsString(EXPIRED_ACCESS_TOKEN.getMessage()));
//    }
//
//    @Test
//    public void ?????????_????????????_?????????_???????????? () throws Exception {
//        //given
//        Member member = Member.createMember(new NickName("?????????"), email,"oAuthPassword1!", Role.USER);
//        SocialLoginToken socialLoginToken = new SocialLoginToken("ACCESS_TOKEN",5000L,"REFRESH_TOKEN",5000L, SocialProvider.PITTER);
//        Token tokenEntity = Token.generateToken(member, socialLoginToken);
//        memberRepository.save(member);
//        tokenRepository.saveAndFlush(tokenEntity);
//        InternalApiRequestToken internalApiRequestToken = tokenEntity.getInternalApiRequestToken();
//
//        tokenType = TokenType.REFRESH_TOKEN;
//        token = internalApiRequestToken.getToken();
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", token);
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(SUCCESS.getCode()));
//        assertThat(response.getContentAsString(), containsString(SUCCESS.getMessage()));
//    }
//
//    @Test
//    public void ?????????_????????????_?????????_???????????? () throws Exception {
//        //given
//        Member member = Member.createMember(new NickName("?????????"), email,"oAuthPassword1!", Role.USER);
//        SocialLoginToken socialLoginToken = new SocialLoginToken("ACCESS_TOKEN",5000L,"REFRESH_TOKEN",5000L, SocialProvider.PITTER);
//        Token tokenEntity = Token.generateToken(member, socialLoginToken);
//        memberRepository.save(member);
//        tokenRepository.saveAndFlush(tokenEntity);
//
//        tokenType = TokenType.REFRESH_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, oneMonthBefore());
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", token);
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(EXPIRED_REFRESH_TOKEN.getMessage()));
//        assertThat(response.getContentAsString(), containsString(EXPIRED_REFRESH_TOKEN.getMessage()));
//    }
//
//    @Test
//    public void DB???_????????????_??????_????????????_?????????_???????????? () throws Exception {
//        //given
//        tokenType = TokenType.REFRESH_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", token);
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(UNIDENTIFIED_REFRESH_TOKEN.getCode()));
//        assertThat(response.getContentAsString(), containsString(UNIDENTIFIED_REFRESH_TOKEN.getMessage()));
//    }
//
//    @Test
//    public void ?????????_Subject_Email_???_???????????? () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2NDMzNTgxODksImV4cCI6MTY3NDg5NDE4OSwiYXVkIjoiIiwic3ViIjoidGVzdGVyMTIzQHBpdHRlci5jb20ifQ.8c6Dq3-4LrhYnEI_Gc7DA1ZrKrqN34OWQ9LZIWswfs0");
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(INVALID_SUBJECT_EMAIL.getCode()));
//        assertThat(response.getContentAsString(), containsString(INVALID_SUBJECT_EMAIL.getMessage()));
//    }
//
//    @Test
//    public void ?????????_SignatureKey???_???????????? () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE2NDMzNTgxODksImV4cCI6MTY3NDg5NDE4OSwiYXVkIjoiIiwic3ViIjoidGVzdGVyQHBpdHRlci5jb20ifQ.nNXoXC2M-8Gn9b5PGViyp8KgJ6C88xSldunmJklR0ds");
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(INVALID_SIGNATURE.getCode()));
//        assertThat(response.getContentAsString(), containsString(INVALID_SIGNATURE.getMessage()));
//    }
//
//    @Test
//    public void ?????????_??????_??????_INVALID_TOKEN_TYPE () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", token);
//        requestBody.put("tokenType", "access_token");
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(INVALID_TOKEN_TYPE.getCode()));
//        assertThat(response.getContentAsString(), containsString(INVALID_TOKEN_TYPE.getMessage()));
//    }
//
//    @Test
//    public void ?????????_??????_??????_NULL_TOKEN_TYPE () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//
//        requestBody.put("email", email.getEmail());
//        requestBody.put("token", token);
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(INVALID_PARAMETER.getCode()));
//        assertThat(response.getContentAsString(), containsString(INVALID_PARAMETER.getMessage()));
//    }
//
//    @Test
//    public void ?????????_??????_??????_NULL_EMAIL () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//
//        requestBody.put("token", token);
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(INVALID_PARAMETER.getCode()));
//        assertThat(response.getContentAsString(), containsString(INVALID_PARAMETER.getMessage()));
//    }
//
//    @Test
//    public void ?????????_??????_??????_NULL_TOKEN () throws Exception {
//        //given
//        tokenType = TokenType.ACCESS_TOKEN;
//        token = JwtUtils.jwtTokenBuilder(email, tokenType, now());
//
//        requestBody.put("email", email.getEmail());
//        requestBody.put("tokenType", tokenType.getKey());
//
//        //when
//        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(requestBody)))
//                .andExpect(status().isBadRequest())
//                .andReturn()
//                .getResponse();
//        //then
//        assertThat(response.getContentAsString(), containsString(INVALID_PARAMETER.getCode()));
//        assertThat(response.getContentAsString(), containsString(INVALID_PARAMETER.getMessage()));
//    }



}