package com.example.spring.service;

import com.example.spring.domain.ElectroType;
import com.example.spring.domain.Employee;
import com.example.spring.repo.ElectroTypeRepo;
import com.example.spring.utils.CsvHelper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
public class ElectroTypeService {

    private final ElectroTypeRepo electroTypeRepo;

    public ElectroTypeService(ElectroTypeRepo electroTypeRepo) {
        this.electroTypeRepo = electroTypeRepo;
    }

    public List<ElectroType> getAll() {
        return electroTypeRepo.findAll();
    }


    public void saveAllCsv(MultipartFile file) {
        CsvHelper.checkIfValidFile(file);
        try {
            List<ElectroType> electroTypeList = CsvHelper.csvToElectroType(file.getInputStream());
            electroTypeRepo.saveAll(electroTypeList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public void saveAllZip(MultipartFile file) throws IOException {
        checkIfValidZipFile(file);
        File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
        FileOutputStream o = new FileOutputStream(zip);
        IOUtils.copy(file.getInputStream(), o);
        o.close();
        ZipFile zipFile = new ZipFile(zip);
        ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.getName().equals("ElectroType.csv")) {
                try (InputStream inputStreamCsv = zipFile.getInputStream(zipEntry)) {
                    List<ElectroType> electroTypeList = CsvHelper.csvToElectroType(inputStreamCsv);
                    electroTypeRepo.saveAll(electroTypeList);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store csv data: " + e.getMessage());
                }
            }

        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }
    private boolean checkIfValidZipFile(MultipartFile file) {
        String fileExension = "ZIP";
        if (file.getOriginalFilename().endsWith(fileExension.toLowerCase()) || file.getOriginalFilename().endsWith(fileExension)) {
            return true;
        }
        return false;
    }
}
