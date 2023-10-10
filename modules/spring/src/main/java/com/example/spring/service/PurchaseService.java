package com.example.spring.service;

import com.example.spring.domain.Electronics;
import com.example.spring.domain.Employee;
import com.example.spring.domain.Purchase;
import com.example.spring.exception.ElectronicsNotFoundCountException;
import com.example.spring.exception.TimeDateException;
import com.example.spring.repo.PurchaseRepo;
import com.example.spring.utils.CsvHelper;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
@Validated
public class PurchaseService {


    private final PurchaseRepo purchaseRepo;
    private final ElectronicsService electronicsService;
    private final EmployeeService employeeService;
    ;

    public PurchaseService(PurchaseRepo purchaseRepo, ElectronicsService electronicsService, EmployeeService employeeService) {
        this.purchaseRepo = purchaseRepo;
        this.electronicsService = electronicsService;
        this.employeeService = employeeService;
    }

    public List<Purchase> getAll() {
        return purchaseRepo.findAll();
    }

    public Integer getAllSaleForCash() {
        return purchaseRepo.getPurchaseByElectroAndPurchaseType();
    }

    public Purchase add(@Valid Purchase purchase) {
        chekingDate(purchase.getPurchaseDate());
        return purchaseRepo.save(purchase);
    }

    public Purchase updatePurchase(Long id, @Valid Purchase purchase) {
        Purchase inDB = purchaseRepo.findById(id).orElseThrow();
        inDB.setEmployeeId(purchase.getEmployeeId());
        chekingDate(purchase.getPurchaseDate());
        inDB.setPurchaseDate(purchase.getPurchaseDate());
        inDB.setElectroId(purchase.getElectroId());
        inDB.setType(purchase.getType());
        return purchaseRepo.save(purchase);
    }

    public void delete(Long id) {
        purchaseRepo.deleteById(id);
    }


    @Transactional
    public Purchase purchase(Long electronicId, Long emplId, @Valid Purchase purchase) {
        Purchase newPurchase = new Purchase();
        Electronics electronics = electronicsService.findById(electronicId);
        Employee employee = employeeService.findById(emplId);
        Integer eLcount = electronics.getCount();
        if (electronics.getCount() > 0 && employee.getElectroTypes().equals(electronics.getEtype())) {
            newPurchase.setElectroId(electronics.getId());
            newPurchase.setType(electronics.getEtype());
            newPurchase.setPurchaseDate(LocalDateTime.now());
            newPurchase.setEmployeeId(employee.getId());
            purchaseRepo.save(newPurchase);
        } else {
            throw new ElectronicsNotFoundCountException("Товара нет в наличие");
        }
        electronics.setCount(eLcount - 1);
        electronicsService.saveOrUpdate(electronics);
        return newPurchase;
    }
    public Purchase findById(Long id){
        return purchaseRepo.findById(id).orElseThrow();
    }

    public void saveCsv(MultipartFile file) {
        CsvHelper.checkIfValidFile(file);
        try {
            List<Purchase> purchases = CsvHelper.csvToPurchase(file.getInputStream());
            purchaseRepo.saveAll(purchases);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public void addZip(MultipartFile file) throws IOException {

        File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
        FileOutputStream o = new FileOutputStream(zip);
        IOUtils.copy(file.getInputStream(), o);
        o.close();
        ZipFile zipFile = new ZipFile(zip);
        ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.getName().equals("Purchase.csv")) {
                try (InputStream inputStreamCsv = zipFile.getInputStream(zipEntry)) {
                    List<Purchase> purchases = CsvHelper.csvToPurchase(inputStreamCsv);
                    purchaseRepo.saveAll(purchases);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store csv data: " + e.getMessage());
                }

            }

        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }


    private boolean chekingDate(LocalDateTime time) {
        if (time.isBefore(LocalDateTime.now())) {
            return true;
        }
        return false;
    }


}



