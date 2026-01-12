package org.gardin.felipe.crypto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CryptoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEncrypt() throws Exception {
        mockMvc.perform(get("/api/encrypt").param("text", "Hello World"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.not("Hello World")))
                .andExpect(content().string(org.hamcrest.Matchers.matchesRegex("^[A-Za-z0-9+/]+={0,2}$")));
    }

    @Test
    public void testDecrypt() throws Exception {
        // First encrypt
        String encrypted = mockMvc.perform(get("/api/encrypt").param("text", "Hello World"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Then decrypt
        mockMvc.perform(get("/api/decrypt").param("text", encrypted))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }
}
