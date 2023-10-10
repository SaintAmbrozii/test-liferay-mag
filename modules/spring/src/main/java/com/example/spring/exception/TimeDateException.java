package com.example.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONTINUE)
public class TimeDateException extends RuntimeException{
    public TimeDateException(String message) {
        super(message);
    }
}
