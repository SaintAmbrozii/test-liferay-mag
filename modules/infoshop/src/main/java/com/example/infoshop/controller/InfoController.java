package com.example.infoshop.controller;

import com.example.infoshop.domain.Employee;
import com.example.infoshop.repo.ElectronicsRepo;
import com.example.infoshop.repo.EmployeeRepo;
import com.example.infoshop.repo.PurchaseRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/info")
public class InfoController {

    private final EmployeeRepo employeeRepo;
    private final ElectronicsRepo electronicsRepo;
    private final PurchaseRepo purchaseRepo;

    public InfoController(EmployeeRepo employeeRepo, ElectronicsRepo electronicsRepo, PurchaseRepo purchaseRepo) {
        this.employeeRepo = employeeRepo;
        this.electronicsRepo = electronicsRepo;
        this.purchaseRepo = purchaseRepo;
    }

    @GetMapping("salephone")
    public List<Employee> BestSalePhone(){
      return employeeRepo.bestsaleforsmatrophone();
    }

    @GetMapping("count")
    public Integer getCountSaleForLastMounth(){
        return electronicsRepo.CountElectronicsSaleForLastMonth();
    }
    @GetMapping("cash")
    public Integer getCountCash(){
        return purchaseRepo.getPurchaseByElectroAndPurchaseType();
    }

}
