package com.example.webchecker_2.dto;

import jakarta.validation.constraints.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteRequest {

    @NotBlank(message = "Name ist erforderlich")
    private String name;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL muss mit http:// oder https:// beginnen")
    private String url;

    private String cssSelector;


    @Min(value =1, message = "Interval muss mindestens eine Minute betragen")
    private int intervalMinutes = 30;


    @Email(message= "E-Mail-Format ungültig")
    @NotBlank(message ="Email ist erforderlich")
    private String recipientEmail;

}
