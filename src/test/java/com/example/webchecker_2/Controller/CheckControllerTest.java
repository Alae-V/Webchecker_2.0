package com.example.webchecker_2.Controller;

import com.example.webchecker_2.controller.CheckController;
import com.example.webchecker_2.model.Website;
import com.example.webchecker_2.repository.WebsiteRepository;
import com.example.webchecker_2.service.PlaywrightScraperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckController.class)
public class CheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebsiteRepository websiteRepository;

    @MockBean
    private PlaywrightScraperService scraperService;

    @Test
    void CheckSingleWebsite_ShouldReturnSuccessMessage(){
        Long websiteId = 1L;
        Website website = Website.builder()
                .id(websiteId)
                .name("Test_V")
                .url("www.google.com")
                .build();

        when(websiteRepository.findById(websiteId)).thenReturn(Optional.of(website));

        mockMvc.perform(post("/api/check/"+ websiteId))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Check für Website 'Test Blog' (ID: " + websiteId + ") abgeschlossen"));

        verify(scraperService,times(1)).checkSingleWebsite(website);
    }

    @Test
    void CheckSingleWebsite_WithInvalidId_ShouldReturn404() throws Exception{
        Long InvalidId = 999L;
        when(websiteRepository.findById(InvalidId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/check/" + InvalidId))
                .andExpect(status().isNotFound());

    }

}
