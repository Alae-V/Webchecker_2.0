package com.example.webchecker_2.service;

import com.example.webchecker_2.dto.*;
import com.example.webchecker_2.model.*;
import com.example.webchecker_2.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class WebCheckService {

    private final ContentItemRepository contentItemRepository;
    private final WebsiteRepository websiteRepository;

    @Transactional
    public WebsiteResponse addWebsite(WebsiteRequest request){

        Website website = Website.builder().
                name(request.getName()).
                url(request.getUrl()).
                cssSelector(request.getCssSelector()).
                intervalMinutes(request.getIntervalMinutes()).
                recipientEmail(request.getRecipientEmail()).
                build();

        Website saved = websiteRepository.save(website);
        log.info("Neue Website hinzugefügt {} (ID: {})", saved.getName(),saved.getId());
        return mapToResponse(saved);
    }

    public List<WebsiteResponse> getAllWebsites(){
        return websiteRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WebsiteResponse getWebsite(Long id){
        Website website= websiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Website nicht gefunden mit ID: " + id));
        return mapToResponse(website);
    }

    @Transactional
    public void deleteWebsite(Long id){
        if(! websiteRepository.existsById(id)){
            throw new IllegalArgumentException("Website nicht gefunden");
        }
        websiteRepository.deleteById(id);
        log.info("Website gelöscht mit ID: {}",id);
    }

    private WebsiteResponse mapToResponse(Website website){

        List<ContentItemResponse> contentResponses = website.getContentItems()
                .stream().map(this::mapToContentResponse).toList();

        return WebsiteResponse.builder()
                .id(website.getId())
                .name(website.getName())
                .url(website.getUrl())
                .cssSelector(website.getCssSelector())
                .intervalMinutes(website.getIntervalMinutes())
                .lastChecked(website.getLastChecked())
                .recipientEmail(website.getRecipientEmail())
                .contentItems(contentResponses)
                .build();
    }

    private ContentItemResponse mapToContentResponse(ContentItem item){
        return ContentItemResponse.builder()
                .id(item.getId())
                .contentText(item.getContentText())
                .contentUrl(item.getContentUrl())
                .detectedAt(item.getDetectedAt())
                .build();
    }


}
