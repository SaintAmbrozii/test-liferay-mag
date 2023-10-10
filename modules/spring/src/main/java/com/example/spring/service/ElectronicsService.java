package com.example.spring.service;


import com.example.spring.domain.Electronics;
import com.example.spring.domain.Employee;
import com.example.spring.domain.Purchase;
import com.example.spring.repo.ElectronicsRepo;
import com.example.spring.utils.CsvHelper;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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
public class ElectronicsService {

    private final ElectronicsRepo electronicsRepo;

    public ElectronicsService(ElectronicsRepo electronicsRepo) {
        this.electronicsRepo = electronicsRepo;
    }

    public List<Electronics> getAllInSale() {
        return electronicsRepo.findElectronicsByCountNotNull();
    }

    public Integer getCountSaleElectronicaForMounth() {
        return electronicsRepo.CountElectronicsSaleForLastMonth();
    }

    public List<Electronics> getAll() {
        return electronicsRepo.findAll();
    }

    public Electronics add(@Valid Electronics electronics) {
        return electronicsRepo.save(electronics);
    }

    public Electronics updateElectronics(Long id, @Valid Electronics electronics) {
        Electronics inDB = electronicsRepo.findById(id).orElseThrow();
        inDB.setName(electronics.getName());
        inDB.setArchive(electronics.getArchive());
        inDB.setInStock(electronics.getInStock());
        inDB.setDescription(electronics.getDescription());
        inDB.setPrice(electronics.getPrice());
        inDB.setCount(electronics.getCount());
        return electronicsRepo.save(inDB);
    }


    public Electronics saveOrUpdate(Electronics electronics) {
        return electronicsRepo.saveAndFlush(electronics);
    }

    public Electronics findById(Long id) {
        return electronicsRepo.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        electronicsRepo.deleteById(id);
    }


    public void saveCsv(MultipartFile file) {
        CsvHelper.checkIfValidFile(file);
        try {
            List<Electronics> electronics = CsvHelper.csvToElectronics(file.getInputStream());
            electronicsRepo.saveAll(electronics);
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
            if (zipEntry.getName().equals("Electronics.csv")) {
                try (InputStream inputStreamCsv = zipFile.getInputStream(zipEntry)) {
                    List<Electronics> electronics = CsvHelper.csvToElectronics(inputStreamCsv);
                    electronicsRepo.saveAll(electronics);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store csv data: " + e.getMessage());

                }
            }
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }
}
