package com.example.webchecker_2.controller;

import com.example.webchecker_2.dto.*;
import com.example.webchecker_2.service.WebCheckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/websites")
@RequiredArgsConstructor
public class WebCheckerController {

    private final WebCheckService webCheckService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WebsiteResponse addwebsite(@Valid @RequestBody WebsiteRequest request){
        return webCheckService.addWebsite(request);
    }

    @GetMapping
    public List<WebsiteResponse> getAllWebsites(){
        return webCheckService.getAllWebsites();
    }

    @GetMapping("/{id}")
    public WebsiteResponse getWebsite(@PathVariable Long id){
        return webCheckService.getWebsite(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWebsite(@PathVariable Long id){
        webCheckService.deleteWebsite(id);
    }

}
