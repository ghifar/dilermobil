package com.ghifar.demoku;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc //we're using mock mvc to test endpoint or any controllers
public class CarRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAuthentication() throws Exception{
        //testing authentication with correct credentials
        this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content("{ \"username\": \"admin\" , \"password\" : \"admin\"}"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

        //testing with wrong credentials
        this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content("{ \"username\": \"admin\" , \"password\" : \"wrongpassword\"}"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }
}
