package com.example.webchecker_2.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="content_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String contentText;

    private String contentUrl;
    private LocalDateTime detectedAt;

    @ManyToOne
    @JoinColumn(name="website_id")
    private Website website;
}