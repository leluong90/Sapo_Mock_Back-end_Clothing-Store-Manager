package sapo.com.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.exception.IncorrectPasswordException;
import sapo.com.exception.UserNotFoundException;
import sapo.com.model.dto.request.PasswordResetRequest;
import sapo.com.model.entity.User;
import sapo.com.service.UserService;

@RestController
public class UserController {
    @Autowired private UserService userService;
    @GetMapping("/staffs/{id}")
    public ResponseEntity<?> findById(@RequestParam Long id) throws UserNotFoundException {
        User staff = userService.findById(id);
        return new ResponseEntity<>(staff, HttpStatus.OK);

    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable("id") Long staffId, @RequestBody PasswordResetRequest passwordResetRequest) throws UserNotFoundException,IncorrectPasswordException {

            userService.resetPassword(staffId, passwordResetRequest);
            return new ResponseEntity<>("Reset password successfully",HttpStatus.OK);

    }







}
