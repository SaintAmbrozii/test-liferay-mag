package com.example.spring.repo;

import com.example.spring.domain.ElectroType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectroTypeRepo extends JpaRepository<ElectroType, Long> {

}
