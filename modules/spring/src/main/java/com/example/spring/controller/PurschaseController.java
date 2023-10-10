package com.example.spring.controller;

import com.example.spring.domain.Purchase;
import com.example.spring.repo.PurchaseRepo;
import com.example.spring.service.PurchaseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("api/purchase")
public class PurschaseController {

    private final PurchaseService purchaseService;


    public PurschaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<Purchase> getAll() {
        return purchaseService.getAll();
    }

    @GetMapping("cash")
    public Integer getAllSaleForCash() {
        return purchaseService.getAllSaleForCash();
    }

    @PostMapping("scv")
    public void saveCsv(@RequestParam("file") MultipartFile file) {
        purchaseService.saveCsv(file);
    }


    @PostMapping("zip")
    public void saveZip(@RequestParam("file") MultipartFile file) throws IOException {
        purchaseService.addZip(file);
    }

    @PostMapping("{elid}/{userid}")
    public Purchase purchase(@PathVariable(name = "elid") Long electroid, @PathVariable(name = "userid") Long userId, @RequestBody Purchase purchase) {
        return purchaseService.purchase(electroid, userId, purchase);
    }
    @PostMapping
    public Purchase add(@RequestBody Purchase purchase) {
        return purchaseService.add(purchase);
    }
    @PutMapping("{id}")
    public Purchase update(@PathVariable(name = "id")Long id,@RequestBody Purchase purchase) {
        return purchaseService.updatePurchase(id, purchase);
    }
    @GetMapping("{id}")
    public Purchase findById(@PathVariable(name = "id")Long id){
        return purchaseService.findById(id);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id")Long id) {
        purchaseService.delete(id);
    }

}
