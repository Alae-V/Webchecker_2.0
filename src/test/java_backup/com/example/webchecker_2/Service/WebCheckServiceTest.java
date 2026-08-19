package com.example.webchecker_2.Service;


import com.example.webchecker_2.dto.WebsiteRequest;
import com.example.webchecker_2.dto.WebsiteResponse;
import com.example.webchecker_2.model.Website;
import com.example.webchecker_2.repository.WebsiteRepository;
import com.example.webchecker_2.service.WebCheckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WebCheckServiceTest {

    @Mock
    private WebsiteRepository websiteRepository;

    @InjectMocks
    private WebCheckService webCheckService;

    @Test
    void addWebsite_ShouldSaveAndReturnWebsite(){

        WebsiteRequest request = new WebsiteRequest();
        request.setName("Test Website");
        request.setUrl("https://www.google.com");
        request.setIntervalMinutes(30);
        request.setRecipientEmail("ala.bensalah5@gmail.com");

        Website savedWebsite = Website.builder()
                .id(1L)
                .name("Test Website")
                .url("https://www.google.com")
                .intervalMinutes(30)
                .recipientEmail("ala.bensalah5@gmail.com")
                .build();

        when(websiteRepository.save(any(Website.class))).thenReturn(savedWebsite);

        WebsiteResponse response = webCheckService.addWebsite(request);

        assertNotNull(response);
        assertEquals(savedWebsite.getName(),response.getName());
        assertEquals(savedWebsite.getUrl(),response.getUrl());
        assertEquals(savedWebsite.getIntervalMinutes(),response.getIntervalMinutes());
        assertEquals(savedWebsite.getRecipientEmail(),response.getRecipientEmail());

        verify(websiteRepository, times(1)).save(any(Website.class));

    }


    @Test
    void getWebsite_WithInvalidId_ShouldThrowException(){
        Long invalidId=999L;
        when(websiteRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, ()-> webCheckService.getWebsite(invalidId));

        verify(websiteRepository,times(1)).findById(invalidId);


    }

}
