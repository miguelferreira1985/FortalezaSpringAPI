package com.fotaleza.fortalezaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SupplierAlreadyExistsException extends RuntimeException {
    public SupplierAlreadyExistsException(String message) {
        super(message);
    }
}
