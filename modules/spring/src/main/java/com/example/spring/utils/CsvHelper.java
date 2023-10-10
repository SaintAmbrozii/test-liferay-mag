package com.example.spring.utils;

import com.example.spring.domain.*;
import com.example.spring.exception.TimeDateException;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvHelper {

    public static boolean checkIfValidFile(MultipartFile file) {
        String fileExension = "CSV";
        if (file.getOriginalFilename().endsWith(fileExension.toLowerCase()) || file.getOriginalFilename().endsWith(fileExension)) {
            return true;
        }
        return false;
    }


    public static List<Employee> csvToEmployee(InputStream is) {

        List<Employee> employees = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.US);
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(is, Charset.forName("windows-1251"));
        parseAllRecords.forEach(record -> {
            Employee employee = new Employee();
            employee.setId(Long.parseLong(record.getString("id")));
            employee.setFirstname(record.getString("firstname"));
            employee.setLastname(record.getString("lastname"));
            employee.setPatronymic(record.getString("patronymic"));
            employee.setBirthdate(LocalDate.parse(record.getString("birthdate"), formatter));
            employee.setPosition(Long.parseLong(record.getString("position")));
            employee.setGender(Boolean.parseBoolean(String.valueOf(record.getInt("gender"))));
            employees.addAll(Collections.singleton(employee));

        });
        return employees;
    }

    public static List<Electronics> csvToElectronics(InputStream is) {
        List<Electronics> electronicsList = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(is, Charset.forName("windows-1251"));
        parseAllRecords.forEach(record -> {
            Electronics electronics = new Electronics();
            electronics.setId(Long.parseLong(record.getString("id")));
            electronics.setName(record.getString("name"));
            electronics.setEtype(Long.parseLong(record.getString("etype")));
            electronics.setPrice(Integer.parseInt(record.getString("price")));
            electronics.setCount(record.getInt("count"));
            electronics.setInStock(Boolean.parseBoolean(String.valueOf((record.getInt("instock")))));
            electronics.setArchive(Boolean.parseBoolean(String.valueOf(((record.getInt("archive"))))));
            electronics.setDescription(record.getString("description"));
            electronicsList.addAll(Collections.singleton(electronics));
        });
        return electronicsList;

    }

    public static List<Purchase> csvToPurchase(InputStream is) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        List<Purchase> purchaseList = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(is, Charset.forName("windows-1251"));
        parseAllRecords.forEach(record -> {
            Purchase purchase = new Purchase();
            purchase.setId(Long.parseLong(record.getString("id")));
            purchase.setPurchaseDate(LocalDateTime.parse(record.getString("purchasedate"), formatter));
            purchase.setElectroId(Long.parseLong(record.getString("electroId")));
            purchase.setEmployeeId(Long.parseLong(record.getString("employeeId")));
            purchase.setType(Long.parseLong(record.getString("type")));
            purchaseList.addAll(Collections.singleton(purchase));
        });
        return purchaseList;
    }

    public static List<PurchaseType> cvsToPurchaseType(InputStream is) {
        List<PurchaseType> purchaseTypeList = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(is, Charset.forName("windows-1251"));
        parseAllRecords.forEach(record -> {
            PurchaseType purchaseType = new PurchaseType();
            purchaseType.setId(Long.parseLong(record.getString("id")));
            purchaseType.setName(record.getString("name"));
            purchaseTypeList.addAll(Collections.singleton(purchaseType));
        });
        return purchaseTypeList;
    }

    public static List<PositionType> csvToPositionType(InputStream is) {
        List<PositionType> positionTypeList = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(is, Charset.forName("windows-1251"));
        parseAllRecords.forEach(record -> {
            PositionType positionType = new PositionType();
            positionType.setId(Long.parseLong(record.getString("id")));
            positionType.setName(record.getString("name"));
            positionTypeList.addAll(Collections.singleton(positionType));
        });
        return positionTypeList;
    }

    public static List<ElectroType> csvToElectroType(InputStream is) {
        List<ElectroType> electroTypeList = new ArrayList<>();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(is, Charset.forName("windows-1251"));
        parseAllRecords.forEach(record -> {
            ElectroType electroType = new ElectroType();
            electroType.setId(Long.parseLong(record.getString("id")));
            electroType.setName(record.getString("name"));
            electroTypeList.addAll(Collections.singleton(electroType));
        });
        return electroTypeList;
    }


}
