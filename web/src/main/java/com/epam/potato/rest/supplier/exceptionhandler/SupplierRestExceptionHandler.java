package com.epam.potato.rest.supplier.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.potato.service.supplier.exception.InvalidSupplierException;
import com.epam.potato.service.supplier.exception.SupplierNotFoundException;
import com.epam.potato.service.supplier.exception.UnableToCreateSupplierException;

@RestControllerAdvice
public class SupplierRestExceptionHandler {

    @ExceptionHandler(value = InvalidSupplierException.class)
    public ResponseEntity<String> handleInvalidSupplierException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = SupplierNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnableToCreateSupplierException.class)
    public ResponseEntity<String> handleUnableToCreateException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }

}
