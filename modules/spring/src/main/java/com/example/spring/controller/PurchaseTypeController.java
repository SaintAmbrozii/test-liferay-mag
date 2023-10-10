package com.example.spring.controller;

import com.example.spring.domain.PurchaseType;
import com.example.spring.service.PurchaseTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/purchasetype")
public class PurchaseTypeController {

    private final PurchaseTypeService purchaseTypeService;

    public PurchaseTypeController(PurchaseTypeService purchaseTypeService) {
        this.purchaseTypeService = purchaseTypeService;
    }

    @GetMapping
    public List<PurchaseType> getAll() {
        return purchaseTypeService.getAll();
    }

    @PostMapping
    public void saveAllCsv(@RequestPart("file") MultipartFile file) {
        purchaseTypeService.saveAllCsv(file);
    }

    @PostMapping("zip")
    public void saveAllZip(@RequestPart("file") MultipartFile file) throws IOException {
        purchaseTypeService.savaAllZip(file);
    }


}
