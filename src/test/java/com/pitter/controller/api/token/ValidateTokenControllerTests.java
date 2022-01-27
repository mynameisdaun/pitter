package com.pitter.controller.api.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitter.controller.dto.TokenCode;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.utils.JwtUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.pitter.controller.dto.TokenCode.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.assertj.core.api.Assertions;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"classpath:/application.properties","classpath:/application-oauth.properties"})
@AutoConfigureMockMvc
@Transactional
public class ValidateTokenControllerTests {

    @Autowired ValidateTokenController validateTokenController;

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired WebApplicationContext context;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void 유효한_엑세스토큰을_검증한다 () throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        Email email = new Email("tester@pitter.com");
        TokenType tokenType = TokenType.ACCESS_TOKEN;
        String token = JwtUtils.jwtTokenBuilder(email, tokenType);

        requestBody.put("email", email.getEmail());
        requestBody.put("token", token);
        requestBody.put("tokenType", tokenType.getKey());


        //when
        MockHttpServletResponse response = mockMvc.perform(post("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse();

        //then
        assertThat(response.getContentAsString(), containsString(SUCCESS.getCode()));
        assertThat(response.getContentAsString(), containsString(SUCCESS.getMessage()));
    }

}