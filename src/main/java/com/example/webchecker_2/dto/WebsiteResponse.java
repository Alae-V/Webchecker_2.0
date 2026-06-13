package com.example.webchecker_2.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebsiteResponse {

    private Long id;
    private String name;
    private String url;
    private String cssSelector;
    private int intervalMinutes;
    private LocalDateTime lastChecked;
    private String recipientEmail;
    private List<ContentItemResponse> contentItems;

}
