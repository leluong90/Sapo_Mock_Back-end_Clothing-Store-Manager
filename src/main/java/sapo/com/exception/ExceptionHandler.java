package sapo.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sapo.com.model.dto.response.ResponseObject;

// inh lớp naày  ngoại lệ chung
@RestControllerAdvice
public class ExceptionHandler extends RuntimeException {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseObject> handleGeneralException(Exception exception){
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build()
        );
    }
    // Xử lý ngoại lệ CustomerNotFoundException
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        // Tạo thông báo lỗi
        String errorMessage = ex.getMessage();

        // Trả về thông báo lỗi và mã trạng thái 404 NOT FOUND
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleStaffNotFoundException(UserNotFoundException ex) {
        // Tạo thông báo lỗi
        String errorMessage = ex.getMessage();

        // Trả về thông báo lỗi và mã trạng thái 404 NOT FOUND
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }



}
