package com.example.webchecker_2.repository;

import com.example.webchecker_2.model.ContentItem;
import com.example.webchecker_2.model.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContentItemRepository extends JpaRepository<ContentItem, Long> {
    List<ContentItem> findByWebsiteId(Long websiteId);
    boolean existsByContentUrl(String contentUrl);
    long countByWebsite(Website website);
}