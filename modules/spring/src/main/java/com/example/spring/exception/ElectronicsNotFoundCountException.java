package com.example.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ElectronicsNotFoundCountException extends RuntimeException {
    public ElectronicsNotFoundCountException(String message) {
        super(message);
    }

}
