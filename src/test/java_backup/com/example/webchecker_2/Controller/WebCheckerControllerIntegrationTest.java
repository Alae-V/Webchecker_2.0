package com.example.webchecker_2.Controller;


import com.example.webchecker_2.model.Website;
import com.example.webchecker_2.repository.WebsiteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebCheckerControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Test
    void addWebsite_ShouldSaveAndReturnWebsite() throws Exception {

        String json = """
                {
                    "name": "Integration Test",
                    "url": "https://integration-test.com",
                    "intervalMinutes": 60,
                    "recipientEmail": "test@example.com"
                }
                """;

        mockMvc.perform(post("/api/websites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Test"))
                .andExpect(jsonPath("$.url").value("https://integration-test.com"))
                .andExpect(jsonPath("$.intervalMinutes").value(60));
    }

    @Test
    void getAllWebsites_ShouldReturnList() throws Exception {

        Website website = Website.builder()
                .name("Test DB")
                .url("https://test-db.com")
                .intervalMinutes(30)
                .recipientEmail("test@example.com")
                .build();
        websiteRepository.save(website);

        mockMvc.perform(get("/api/websites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test DB"));
    }

    @Test
    void getWebsite_WithValidId_ShouldReturnWebsite() throws Exception {

        Website website = Website.builder()
                .name("Find Me")
                .url("https://find-me.com")
                .intervalMinutes(30)
                .recipientEmail("test@example.com")
                .build();
        Website saved = websiteRepository.save(website);


        mockMvc.perform(get("/api/websites/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Find Me"))
                .andExpect(jsonPath("$.id").value(saved.getId()));
    }

    @Test
    void deleteWebsite_ShouldRemoveWebsite() throws Exception {

        Website website = Website.builder()
                .name("Delete Me")
                .url("https://delete-me.com")
                .intervalMinutes(30)
                .recipientEmail("test@example.com")
                .build();
        Website saved = websiteRepository.save(website);

        mockMvc.perform(delete("/api/websites/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/websites/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    }




