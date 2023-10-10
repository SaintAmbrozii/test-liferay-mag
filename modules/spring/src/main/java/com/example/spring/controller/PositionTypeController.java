package com.example.spring.controller;

import com.example.spring.domain.PositionType;
import com.example.spring.service.PositionTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/positiontype")
public class PositionTypeController {

    private final PositionTypeService positionTypeService;

    public PositionTypeController(PositionTypeService positionTypeService) {
        this.positionTypeService = positionTypeService;
    }

    @GetMapping
    public List<PositionType> getAll() {
        return positionTypeService.getAll();
    }

    @PostMapping
    public void saveAllCsvPositionType(@RequestPart("file") MultipartFile multipartFile) {
        positionTypeService.saveAllCsv(multipartFile);
    }

    @PostMapping("zip")
    public void saveAllZip(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        positionTypeService.saveAllZip(multipartFile);
    }
}
