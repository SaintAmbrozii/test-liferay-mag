package com.example.spring.service;

import com.example.spring.domain.ElectroType;
import com.example.spring.domain.Employee;
import com.example.spring.domain.PositionType;
import com.example.spring.domain.PurchaseType;
import com.example.spring.repo.PositionTypeRepo;
import com.example.spring.utils.CsvHelper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
public class PositionTypeService {

    private final PositionTypeRepo positionTypeRepo;

    public PositionTypeService(PositionTypeRepo positionTypeRepo) {
        this.positionTypeRepo = positionTypeRepo;
    }

    public List<PositionType> getAll() {
        return positionTypeRepo.findAll();
    }


    public void saveAllCsv(MultipartFile file){
        try {
              List<PositionType> positionTypeList = CsvHelper.csvToPositionType(file.getInputStream());
              positionTypeRepo.saveAll(positionTypeList);
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }

    }

    public void saveAllZip(MultipartFile file) throws IOException {
        File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
        FileOutputStream o = new FileOutputStream(zip);
        IOUtils.copy(file.getInputStream(), o);
        o.close();
        ZipFile zipFile = new ZipFile(zip);
        ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.getName().equals("PositionType.csv")) {
                try (InputStream inputStreamCsv = zipFile.getInputStream(zipEntry)) {
                    List<PositionType> electroTypeList = CsvHelper.csvToPositionType(inputStreamCsv);
                    positionTypeRepo.saveAll(electroTypeList);
                } catch (IOException e) {
                    throw new RuntimeException("fail to store csv data: " + e.getMessage());
                }
            }

        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }
}
