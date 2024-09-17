package sapo.com.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import sapo.com.model.dto.response.ResponseObject;

public class ProductException extends Exception{
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ResponseObject response = new ResponseObject("Validation failed", HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle custom exceptions (e.g., ResourceNotFoundException)
    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseObject> handleResourceNotFound(ResourceNotFoundException ex) {
        ResponseObject response = new ResponseObject(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }}
