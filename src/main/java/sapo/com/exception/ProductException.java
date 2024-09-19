package sapo.com.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sapo.com.model.dto.response.ResponseObject;
@RestControllerAdvice
public class ProductException extends Exception{
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldErrors().get(0);

        // Create a response object with the single error
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put(error.getField(), error.getDefaultMessage());

        ResponseObject response = new ResponseObject(ex.getMessage(), errorDetails);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ResponseObject> handleDataConflictException(DataConflictException ex) {
        ResponseObject response = new ResponseObject(ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Handle custom exceptions (e.g., ResourceNotFoundException)
    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseObject> handleResourceNotFound(ResourceNotFoundException ex) {
        ResponseObject response = new ResponseObject(ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
