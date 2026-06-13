package com.example.webchecker_2.dto;


import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentItemResponse {

    private Long id;
    private String contentText;
    private String contentUrl;
    private LocalDateTime detectedAt;

}
