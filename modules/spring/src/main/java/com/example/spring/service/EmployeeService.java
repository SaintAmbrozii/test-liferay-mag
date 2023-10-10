package com.example.spring.service;

import com.example.spring.domain.Electronics;
import com.example.spring.domain.Employee;
import com.example.spring.exception.ElectronicsNotFoundCountException;
import com.example.spring.exception.TimeDateException;
import com.example.spring.repo.EmployeeRepo;
import com.example.spring.utils.CsvHelper;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;



@Service
@Validated
public class EmployeeService {





    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    public List<Employee> getBestSaleSmartphone() {
        return employeeRepo.bestsaleforsmatrophone();
    }

    public Employee getBestSalesEceltronics() {
        return employeeRepo.getBestEmloyees();
    }

    public Employee add(@Valid Employee employee) {

        if (LocalDate.now().isBefore(employee.getBirthdate())) {
            throw new TimeDateException("Вводимая дата больше текущей");
        }

        return employeeRepo.save(employee);

    }

    public Employee findById(Long id) {
        return employeeRepo.findById(id).orElseThrow();
    }

    public Employee update(Long id, @Valid Employee employee) {
        Employee inDB = employeeRepo.findById(id).orElseThrow();
        if (LocalDate.now().isBefore(employee.getBirthdate())) {
            throw new TimeDateException("Вводимая дата больше текущей");
        }
        inDB.setFirstname(employee.getFirstname());
        inDB.setLastname(employee.getLastname());
        inDB.setPatronymic(employee.getPatronymic());
        inDB.setGender(employee.getGender());
        inDB.setBirthdate(employee.getBirthdate());
        return employeeRepo.save(inDB);
    }

    public void employeeDel(Long id) {
        employeeRepo.deleteById(id);
    }





    public void saveAllCsv(MultipartFile file) {
        CsvHelper.checkIfValidFile(file);
        try {
            List<Employee> employeeList = CsvHelper.csvToEmployee(file.getInputStream());
            employeeRepo.saveAll(employeeList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public void addZip(MultipartFile file) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US);
        File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
        FileOutputStream o = new FileOutputStream(zip);
        IOUtils.copy(file.getInputStream(), o);
        o.close();
        ZipFile zipFile = new ZipFile(zip);
        ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            if (zipEntry.getName().equals("Employee.csv")) {
                try (InputStream inputStreamCsv = zipFile.getInputStream(zipEntry)) {
                    List<Employee> employeeList = CsvHelper.csvToEmployee(inputStreamCsv);
                    employeeRepo.saveAll(employeeList);
                    }
                catch (IOException e) {
                    throw new RuntimeException("fail to store csv data: " + e.getMessage());

                }
            }
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }


}
