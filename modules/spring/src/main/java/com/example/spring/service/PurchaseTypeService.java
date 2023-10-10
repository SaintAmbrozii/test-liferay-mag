package com.example.spring.service;

import com.example.spring.domain.Employee;
import com.example.spring.domain.PositionType;
import com.example.spring.domain.PurchaseType;
import com.example.spring.repo.PurchaseTypeRepo;
import com.example.spring.utils.CsvHelper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
public class PurchaseTypeService {

    private final PurchaseTypeRepo purchaseTypeRepo;

    public PurchaseTypeService(PurchaseTypeRepo purchaseTypeRepo) {
        this.purchaseTypeRepo = purchaseTypeRepo;
    }

    public List<PurchaseType> getAll() {
        return purchaseTypeRepo.findAll();
    }

    public void saveAllCsv(MultipartFile file) {
        CsvHelper.checkIfValidFile(file);
        try {
            List<PurchaseType> purchaseTypeList = CsvHelper.cvsToPurchaseType(file.getInputStream());
            purchaseTypeRepo.saveAll(purchaseTypeList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public void savaAllZip(MultipartFile file) throws IOException {
        File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
        FileOutputStream o = new FileOutputStream(zip);
        IOUtils.copy(file.getInputStream(), o);
        o.close();
        ZipFile zipFile = new ZipFile(zip);
        ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.getName().equals("PurchaseType.csv")) {
                try (InputStream inputStreamCsv = zipFile.getInputStream(zipEntry)) {
                    List<PurchaseType> purchaseTypeList = CsvHelper.cvsToPurchaseType(inputStreamCsv);
                    purchaseTypeRepo.saveAll(purchaseTypeList);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store csv data: " + e.getMessage());
                }
            }

        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }
}
