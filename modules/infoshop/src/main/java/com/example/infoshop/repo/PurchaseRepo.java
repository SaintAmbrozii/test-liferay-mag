package com.example.infoshop.repo;

import com.example.infoshop.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface PurchaseRepo extends JpaRepository<Purchase, Long> {

    @Query(value = "SELECT sum(e.price) FROM electronics e " +
            "inner join purchase p ON e.id = p.electro_id " +
            "inner join purchase_type pt ON pt.id = p.type WHERE pt.id = 1", nativeQuery = true)
    Integer getPurchaseByElectroAndPurchaseType();

    @Query(value = "SELECT * FROM purchase p WHERE p.purchase_date =:purchase_date", nativeQuery = true)
    List<Purchase> findAllByPurchaseDate(LocalDate purchase_date);


}
