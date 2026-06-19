package com.example.webchecker_2.scheduler;


import com.example.webchecker_2.service.PlaywrightScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContentCheckScheduler {

    private final PlaywrightScraperService scraperService;

    //Läuft jede 15 Minuten
    @Scheduled(fixedRate = 900000)
    public void CheckDuewebsites(){
        scraperService.checkDueWebsites();
    }

}
