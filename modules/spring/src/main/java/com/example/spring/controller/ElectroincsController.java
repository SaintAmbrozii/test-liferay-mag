package com.example.spring.controller;

import com.example.spring.domain.Electronics;
import com.example.spring.service.ElectronicsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/electronics")
public class ElectroincsController {

    private final ElectronicsService electronicsService;


    public ElectroincsController(ElectronicsService electronicsService) {
        this.electronicsService = electronicsService;
    }

    @GetMapping
    public List<Electronics> getAll() {
        return electronicsService.getAll();
    }

    @GetMapping("sale")
    public List<Electronics> getAllInSale() {
        return electronicsService.getAllInSale();
    }

    @GetMapping("count")
    public Integer getCountSaleForMounth() {
        return electronicsService.getCountSaleElectronicaForMounth();
    }

    @PostMapping("csv")
    public void addCsvElectronics(@RequestParam("file") MultipartFile file) {
        electronicsService.saveCsv(file);
    }

    @PostMapping("zip")
    public void saveAllZip(@RequestParam("file") MultipartFile file) throws IOException {
        electronicsService.addZip(file);
    }
    @PostMapping
    public Electronics add(@RequestBody Electronics electronics) {
        return electronicsService.add(electronics);
    }
    @GetMapping("{id}")
    public Electronics findById(@PathVariable(name = "id")Long id) {
        return electronicsService.findById(id);
    }
    @PutMapping("{id}")
    public Electronics update(@PathVariable(name = "id")Long id,@RequestBody Electronics electronics) {
        return electronicsService.updateElectronics(id, electronics);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") Long id){
        electronicsService.delete(id);
    }
}
