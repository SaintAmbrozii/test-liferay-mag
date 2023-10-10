package com.example.spring.controller;

import com.example.spring.domain.ElectroType;
import com.example.spring.service.ElectroTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/electrotype")
public class ElectroTypeController {

    private final ElectroTypeService electroTypeService;

    public ElectroTypeController(ElectroTypeService electroTypeService) {
        this.electroTypeService = electroTypeService;
    }

    @GetMapping
    public List<ElectroType> getAll() {
        return electroTypeService.getAll();
    }

    @PostMapping
    public void saveAllCsv(@RequestPart("file") MultipartFile file) {
        electroTypeService.saveAllCsv(file);
    }

    @PostMapping("zip")
    public void saveAllZip(@RequestPart("file") MultipartFile file) throws IOException {
        electroTypeService.saveAllZip(file);
    }


}
