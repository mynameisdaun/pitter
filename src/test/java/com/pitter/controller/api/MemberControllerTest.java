package com.pitter.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitter.controller.api.member.MemberController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(properties = "spring.config.location=classpath:/application.properties,classpath:/application-oauth.properties")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTest {

    @Autowired
    MemberController memberController;

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired WebApplicationContext context;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void join_success() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "test1@pitter.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_Invalid_MemberRequestDto_nickName_null () throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "test1@pitter.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_Invalid_MemberRequestDto_nickName_empty () throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "");
        requestBody.put("email", "test1@pitter.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_Invalid_MemberRequestDto_nickName_whitespace () throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", " ");
        requestBody.put("email", "test1@pitter.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_Invalid_MemberRequestDto_nickName_should_be_greaterOrEquals_than_2() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "t");
        requestBody.put("email", "test1@pitter.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_Invalid_MemberRequestDto_nickName_should_be_LessOrEquals_than_10() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "testertester12");
        requestBody.put("email", "test1@pitter.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_invalid_MemberJoinRequest_email_null() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_invalid_MemberRequestDto_email_empty() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_invalid_MemberRequestDto_email_whitespace() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", " ");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_invalid_MemberRequestDto_email_no_special_character() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester!gmail.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_invalid_MemberRequestDto_email_no_companyname() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_invalid_MemberRequestDto_email_no_companyName_dot() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@test");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //TODO: Global 예외처리로 변경해줘야 한다. IllegalArgumentException -> Status BadRequest
    @Test(expected = Exception.class)
    public void join_fail_invalid_MemberRequestDto_email_no_account_email() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "@test.com");
        requestBody.put("password", "xpTmxm1!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void join_fail_invalid_MemberRequestDto_password_null() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@pitter.com");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void join_fail_invalid_MemberRequestDto_password_empty() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@pitter.com");
        requestBody.put("password", "");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void join_fail_invalid_MemberRequestDto_password_whitespace() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@pitter.com");
        requestBody.put("password", " ");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void join_fail_invalid_MemberRequestDto_password_no_upperCase() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@pitter.com");
        requestBody.put("password", "xptmxm12!");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void join_fail_invalid_MemberRequestDto_password_no_special_Character() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@pitter.com");
        requestBody.put("password", "xptmxm123");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void join_fail_invalid_MemberRequestDto_password_no_number() throws Exception {
        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nickName", "tester");
        requestBody.put("email", "tester@pitter.com");
        requestBody.put("password", "xptmxm!@!@a");

        //when
        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


}