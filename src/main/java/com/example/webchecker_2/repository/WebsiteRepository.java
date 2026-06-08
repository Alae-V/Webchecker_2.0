package com.example.webchecker_2.repository;

import com.example.webchecker_2.model.Website;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteRepository extends JpaRepository<Website, Long> {
}