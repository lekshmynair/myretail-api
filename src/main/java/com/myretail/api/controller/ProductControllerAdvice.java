package com.myretail.api.controller;

import com.myretail.api.controller.dto.ErrorDTO;
import com.myretail.api.exception.ApplicationException;
import com.myretail.api.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller Advice to handle exceptions from the controller
 */
@ControllerAdvice
public class ProductControllerAdvice {
    /**
     * @param e   exception
     * @param req webrequest
     * @return ErrorDTO with error details
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(NotFoundException e, WebRequest req) {
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(),
                "product-not-found", "https://api.myretail.com/product-not-found",
                "Product not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * @param e   exception
     * @param req webrequest
     * @return ErrorDTO with error detail
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException e, WebRequest req) {
        ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "internal-server-error", "https://api.myretail.com/internal-server-error",
                "Error processing request");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleFatalException(Exception e, WebRequest req) {
        ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "internal-server-error", "https://api.myretail.com/internal-server-error",
                "Error processing request, please try again");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
