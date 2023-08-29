package com.takehometask.BetMachine.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takehometask.BetMachine.command.GenerateServerNumberCommand;
import com.takehometask.BetMachine.model.BetRequestModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BetMachineRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private GenerateServerNumberCommand generateServerNumberCommand;


    @Test
    public void withInvalidParameters_returnBadRequest() throws Exception {
        String invalidRequest = "{\"number\":1, \"bet\":\"x\"}";

        MockHttpServletRequestBuilder builder  =
                MockMvcRequestBuilders.post("/game/bet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest);

        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void withTooGreatNumber_returnBadRequest() throws Exception {
        BetRequestModel betRequest = new BetRequestModel(
                250,
                BigDecimal.valueOf(40.5)
        );

        MockHttpServletRequestBuilder builder  =
                MockMvcRequestBuilders.post("/game/bet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequest));

        mockMvc.perform(builder)
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Field number -- Number must be less than 101"));
    }

    @Test
    public void ifServerNumberSmaller_returnWin() throws Exception {
        BetRequestModel betRequest = new BetRequestModel(
                50,
                BigDecimal.valueOf(40.5)
        );

        Mockito.doReturn(1).when(generateServerNumberCommand).execute();

        MockHttpServletRequestBuilder builder  =
                MockMvcRequestBuilders.post("/game/bet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequest));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.win", is(80.19)));
    }

    @Test
    public void ifServerNumberGreater_returnNull() throws Exception {
        BetRequestModel betRequest = new BetRequestModel(
                50,
                BigDecimal.valueOf(40.5)
        );

        Mockito.doReturn(100).when(generateServerNumberCommand).execute();

        MockHttpServletRequestBuilder builder  =
                MockMvcRequestBuilders.post("/game/bet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequest));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.win", is(0)));
    }




}
