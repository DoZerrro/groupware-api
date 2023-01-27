package com.dq.aquaranth.menu.controller;

import com.dq.aquaranth.login.dto.LoginReqDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
class MenuCodesControllerTest {

    private static final String API_URL = "/api/menu";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    // FIXME : ERROR 401
    @BeforeEach
    void login() throws Exception {
        //given
        String username = "admin";
        String password = "admin";
        LoginReqDTO loginReqDTO = new LoginReqDTO();
        loginReqDTO.setUsername(username);
        loginReqDTO.setPassword(password);

        //when
        String httpBody = objectMapper.writeValueAsString(loginReqDTO);

        //then
        mockMvc.perform(post("/api/login").content(httpBody).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(log::info);
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(get("/api/menu/gnb")).andExpect(status().isOk());
    }

}
