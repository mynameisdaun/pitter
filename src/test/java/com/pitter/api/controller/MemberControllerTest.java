package com.pitter.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired MemberController memberController;

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
    
    @Test
    public void join_fail_nickName_null() throws Exception {
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
        //then
    }

    @Test
    public void join_fail_nickName_length_should_be_GreaterOrEqualThan_2() throws Exception {
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
        //then
    }

    @Test
    public void join_fail_nickName_length_should_be_lt_2() throws Exception {
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
        //then
    }


}