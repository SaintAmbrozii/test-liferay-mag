package com.example.spring.repo;

import com.example.spring.domain.PurchaseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseTypeRepo extends JpaRepository<PurchaseType, Long> {
}
