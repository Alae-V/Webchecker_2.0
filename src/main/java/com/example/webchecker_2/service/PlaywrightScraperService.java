package com.example.webchecker_2.service;

import com.example.webchecker_2.model.*;
import com.example.webchecker_2.repository.*;
import com.microsoft.playwright.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaywrightScraperService {

    private final ContentItemRepository contentItemRepository;
    private final WebsiteRepository websiteRepository;
    private final Mailservice mailservice;


    /**
     * Prüft alle Websites, die laut Intervall fällig sind
     */

    public void checkDueWebsites(){
        List<Website> websites = websiteRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for(Website website : websites){
            if (isDue(website,now)){
                checkSingleWebsite(website);
            }
        }

    }

    /**
     * prüft eine einzelne Website sofort
     */

    public void checkSingleWebsite(Website website){
        log.info("Starte Check für {} ({})",website.getName(),website.getUrl());

        try(Playwright playwright = Playwright.create()){

            Browser browser = playwright.chromium().launch(new
                    BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            page.navigate(website.getUrl());
            page.waitForLoadState(Page.LoadState.NETWORKIDLE);
            page.waitForTimeout(3000);

            // Extrahiere relevante Elemente

            List<ElementHandle> elements = page.querySelectorAll(
                    "a, h1, h2, h3, h4, h5, h6, p, span, article, div, li");

            List<ContentItem> newItems = new ArrayList<>();
            long existingCount =contentItemRepository.countByWebsite(website);

            for(ElementHandle element : elements){
                String text = element.innerText().trim();
                String href = element.getAttribute("href");

                if(!text.isEmpty() && (href == null || href.startsWith("https"))){
                    if(href == null){href = website.getUrl();}

                    if(!contentItemRepository.existsByContentUrl(href)){

                        ContentItem newItem = ContentItem.builder()
                                .contentText(text)
                                .contentUrl(href)
                                .detectedAt(LocalDateTime.now())
                                .website(website)
                                .build();
                        contentItemRepository.save(newItem);
                        newItems.add(newItem);
                    }
                }
            }

            website.setLastChecked(LocalDateTime.now());
            websiteRepository.save(website);

            log.info("Check für {} abgeschlossen: {} neuen Inhalte gefunden",website.getName(),newItems.size());

            if(existingCount > 0 && !newItems.isEmpty()){
                mailservice.sendSummaryMail(website.getRecipientEmail(),website.getName(),newItems);
            }
            browser.close();


        }
        catch(Exception e){
            log.error("Fehler beimPrüfen vom {}: {}",website.getName(),e.getMessage());
        }


    }

    private boolean isDue(Website website, LocalDateTime now){

        if(website.getLastChecked()==null){
            return true;
        }
        return website.getLastChecked().plusMinutes(website.getIntervalMinutes()).isBefore(now);

    }4

}
