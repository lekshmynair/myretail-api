package com.myretail.api.controller;

import com.myretail.api.controller.dto.ErrorDTO;
import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ProductControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(NotFoundException e, WebRequest req) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(),
                "product-not-found", "https://api.myretail.com/product-not-found",
                "Product not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleFatalException(ApplicationException e, WebRequest req) {
        ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "internal-server-error", "https://api.myretail.com/internal-server-error",
                "Error retrieving produt details");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
