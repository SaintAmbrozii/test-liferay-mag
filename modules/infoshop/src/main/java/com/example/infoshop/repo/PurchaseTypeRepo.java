package com.example.infoshop.repo;

import com.example.infoshop.domain.PurchaseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseTypeRepo extends JpaRepository<PurchaseType, Long> {
}
