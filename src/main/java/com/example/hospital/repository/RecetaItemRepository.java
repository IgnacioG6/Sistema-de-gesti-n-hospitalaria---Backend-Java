package com.example.hospital.repository;

import com.example.hospital.model.RecetaItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaItemRepository extends JpaRepository<RecetaItem, Long> {
}
