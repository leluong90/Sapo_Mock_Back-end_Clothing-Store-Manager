package sapo.com.controller.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.exception.IncorrectPasswordException;
import sapo.com.exception.StaffNotFoundException;
import sapo.com.model.dto.request.PasswordResetRequest;
import sapo.com.model.entity.Staff;
import sapo.com.service.StaffService;

@RestController
public class StaffRestController {
    @Autowired private StaffService staffService;
    @GetMapping("/staffs/{id}")
    public ResponseEntity<?> findById(@RequestParam Integer id) throws StaffNotFoundException {
        Staff staff = staffService.findById(id);
        return new ResponseEntity<>(staff, HttpStatus.OK);

    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable("id") Integer staffId, @RequestBody PasswordResetRequest passwordResetRequest) {
        try {
            staffService.resetPassword(staffId, passwordResetRequest);
            return new ResponseEntity<>("Reset password successfully",HttpStatus.OK);
        } catch (IncorrectPasswordException e) {
            return new ResponseEntity<>("Current password is incorrect",HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







}
