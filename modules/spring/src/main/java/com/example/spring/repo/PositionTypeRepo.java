package com.example.spring.repo;

import com.example.spring.domain.PositionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionTypeRepo extends JpaRepository<PositionType, Long> {
}
