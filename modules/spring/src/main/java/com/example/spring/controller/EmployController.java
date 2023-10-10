package com.example.spring.controller;

import com.example.spring.domain.ElectroType;
import com.example.spring.domain.Employee;
import com.example.spring.repo.EmployeeRepo;
import com.example.spring.service.EmployeeService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/employees")
public class EmployController {
    private final EmployeeService employeeService;


    public EmployController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    @PostMapping(value = "uploads")
    public void uploadCsv(@RequestParam("file") MultipartFile file) throws IOException {

        employeeService.saveAllCsv(file);
    }

    @GetMapping("sale")
    public List<Employee> bestSaleForSmartphone() {
        return employeeService.getBestSaleSmartphone();
    }

    @GetMapping("best")
    public Employee bestSales(){
        return employeeService.getBestSalesEceltronics();
    }



    @PostMapping("zip")
    public void saveAllZip(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.addZip(file);
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAll());
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.add(employee));
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(name = "id") Long id, @RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(id, employee));
    }

    @DeleteMapping("{id}")
    public void deleteEmployee(@PathVariable(name = "id") Long id) {
        employeeService.employeeDel(id);
    }
}
