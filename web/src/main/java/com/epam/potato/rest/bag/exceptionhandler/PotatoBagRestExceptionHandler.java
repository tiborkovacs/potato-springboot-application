package com.epam.potato.rest.bag.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.potato.service.bag.exception.InvalidPotatoBagException;
import com.epam.potato.service.bag.exception.PotatoBagNotFoundException;
import com.epam.potato.service.bag.exception.UnableToCreatePotatoBagException;

@RestControllerAdvice
public class PotatoBagRestExceptionHandler {

    @ExceptionHandler(value = InvalidPotatoBagException.class)
    public ResponseEntity<String> handleInvalidPotatoBagException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = PotatoBagNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnableToCreatePotatoBagException.class)
    public ResponseEntity<String> handleUnableToCreateException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.FORBIDDEN);
    }

}
