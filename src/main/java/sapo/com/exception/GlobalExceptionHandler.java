package sapo.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Xử lý ngoại lệ CustomerNotFoundException
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        // Tạo thông báo lỗi
        String errorMessage = ex.getMessage();

        // Trả về thông báo lỗi và mã trạng thái 404 NOT FOUND
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<?> handleStaffNotFoundException(StaffNotFoundException ex) {
        // Tạo thông báo lỗi
        String errorMessage = ex.getMessage();

        // Trả về thông báo lỗi và mã trạng thái 404 NOT FOUND
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }



}
