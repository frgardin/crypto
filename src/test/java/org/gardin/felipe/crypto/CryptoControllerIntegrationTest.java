package org.gardin.felipe.crypto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CryptoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEncrypt() throws Exception {
        String json = "{\"text\":\"Hello World\"}";

        mockMvc.perform(post("/api/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").exists())
                .andExpect(jsonPath("$.text").isString())
                .andExpect(jsonPath("$.text").isNotEmpty());
    }

    @Test
    public void testDecrypt() throws Exception {
        // First encrypt
        String encryptJson = "{\"text\":\"Hello World\"}";

        String encryptedResponse = mockMvc.perform(post("/api/encrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(encryptJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Extract encrypted text from JSON response
        String encryptedText = encryptedResponse.replace("{\"text\":\"", "").replace("\"}", "");

        // Then decrypt
        String decryptJson = "{\"text\":\"" + encryptedText + "\"}";

        mockMvc.perform(post("/api/decrypt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(decryptJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"text\":\"Hello World\"}"));
    }

    @Test
    public void testHash() throws Exception {
        String json = "{\"text\":\"Hello World\"}";

        mockMvc.perform(post("/api/hash")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"text\":\"a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e\"}"));
    }
}
