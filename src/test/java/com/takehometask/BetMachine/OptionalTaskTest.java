package com.takehometask.BetMachine;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.takehometask.BetMachine.model.BetRequestModel;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OptionalTaskTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test(threadPoolSize=24, invocationCount = 1000000, groups = "optional")
    public void testWithThreads_A() throws Exception {
       // System.out.println("Thread ID: "+Thread.currentThread().getId());
       /** BigDecimal rftp = BigDecimal.ZERO;

        BetRequestModel betRequest = new BetRequestModel(
                50,
                BigDecimal.valueOf(40.5)
        );

        MockHttpServletRequestBuilder builder  =
                MockMvcRequestBuilders.post("/game/bet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(betRequest));

        String result = mockMvc.perform(builder).andReturn().getResponse().getContentAsString();

        System.out.println("Result : " + result);**/
       Thread.currentThread().
    }
}
