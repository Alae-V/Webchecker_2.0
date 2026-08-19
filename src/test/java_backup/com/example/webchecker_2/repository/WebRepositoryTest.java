package com.example.webchecker_2.repository;


import com.example.webchecker_2.model.Website;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class WebRepositoryTest {


    @Autowired
    private WebsiteRepository websiteRepository;

    @Test
    void saveWebsite_ShouldGenerateId(){

        Website website = Website.builder()
                .name("Repo Test")
                .url("https://repo_test.com")
                .intervalMinutes(60)
                .recipientEmail("Test.benTest5@gmail.com")
                .build();

        Website saved = websiteRepository.save(website);

        assertNotNull(saved.getId());
        assertEquals(saved.getName(),website.getName());
        assertEquals(saved.getUrl(),website.getUrl());

    }

    @Test
    void findById_ShouldReturnWebsite(){

        Website website = Website.builder()
                .id(99L)
                .name("Repo Test")
                .url("https://repo_test.com")
                .intervalMinutes(60)
                .recipientEmail("Test.benTest5@gmail.com")
                .build();

        Website saved = websiteRepository.save(website);

        var found = websiteRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(found.get().getName(),website.getName());

    }


}
