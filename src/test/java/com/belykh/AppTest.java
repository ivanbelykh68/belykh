package com.belykh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void apiInfo() throws Exception {
        mvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(content().string("belykh"));
    }

    @Test
    public void words() throws Exception {
        mvc.perform(post("/api/words")
                .param("input", " hi there humanity I am able to code this or that ")
                .param("minSize", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'a': ['able', 'am'], 'h': ['humanity', 'hi'], 't': ['there', 'that', 'this', 'to']}"));
    }

}