package com.example.spring.repo;

import com.example.spring.domain.Electronics;
import com.example.spring.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ElectronicsRepo extends JpaRepository<Electronics, Long> {

    @Query(value = "SELECT e FROM electronics e WHERE e.count > 0")
    List<Electronics> findElectronicsByCountNotNull();

    @Query(value = "SELECT count(e) FROM electronics e" +
            " inner join purchase p ON p.electro_id = e.id" +
            " WHERE p.purchase_date > current_timestamp - INTERVAL '1 MONTH' and e.price < 1500000", nativeQuery = true)
    Integer CountElectronicsSaleForLastMonth();


}
