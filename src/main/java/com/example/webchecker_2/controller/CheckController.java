package com.example.webchecker_2.controller;


import com.example.webchecker_2.dto.WebsiteResponse;
import com.example.webchecker_2.model.Website;
import com.example.webchecker_2.repository.WebsiteRepository;
import com.example.webchecker_2.service.PlaywrightScraperService;
import com.example.webchecker_2.service.WebCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class CheckController {

    private final PlaywrightScraperService scraperService;
    private final WebsiteRepository websiteRepository;

    @PostMapping("/All")
    public String CheckAllWebsites(){
        scraperService.checkDueWebsites();
        return "Alle fälligen Websites wurden geprüft";
    }

    @PostMapping("/{id}")
    public String CheckSingleWebsite(@PathVariable Long id ){

        Website website = websiteRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Website nicht gefunden mit ID:"+id));
        scraperService.checkSingleWebsite(website);
        return "Check für Website: " + website.getName() + " (ID: " + id + ") abgeschlossen";

    }

}
