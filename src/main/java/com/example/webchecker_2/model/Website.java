package com.example.webchecker_2.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="websites")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false,unique = true)
    private String url;


    @Column(name= "interval_minutes")
    private int intervalMinutes = 30;

    @Column(name = "last_checked")
    private LocalDateTime lastChecked;

    @Column(name="recipient_email")
    private String recipientEmail;

    @OneToMany(mappedBy="website", cascade=CascadeType.ALL,orphanRemoval=true)
    @Builder.Default
    private List<ContentItem> contentItems = new ArrayList<>();

    public void addContentItem(ContentItem item){
        contentItems.add(item);
        item.setWebsite(this);
    }

}
