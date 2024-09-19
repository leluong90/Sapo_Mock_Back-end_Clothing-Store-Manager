package sapo.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sapo.com.exception.IncorrectPasswordException;
import sapo.com.exception.StaffNotFoundException;
import sapo.com.model.dto.request.PasswordResetRequest;
import sapo.com.model.entity.Staff;
import sapo.com.repository.StaffRepository;

import java.util.Optional;

@Service
public class StaffService {
    @Autowired private StaffRepository staffRepository;
    public Staff findById(Integer id) throws StaffNotFoundException{
        Optional<Staff> staff = staffRepository.findById(id);
        if(staff.isPresent()){
            return staff.get();
        }else{
            throw new StaffNotFoundException("Khong tim thay staff voi ID:"+id);
        }
    }



    public void resetPassword(Integer staffId, PasswordResetRequest request) throws IncorrectPasswordException, StaffNotFoundException {
        // Fetch staff by id
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id: " + staffId));

        // Verify current password
        if (!staff.getPassword().equals(request.getCurrentPassword())) {
            throw new IncorrectPasswordException("Current password is incorrect");
        }

        // Optional: You may want to add validation to check if newPassword equals confirmNewPassword
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        // Update staff password
        staff.setPassword(request.getNewPassword());
        staffRepository.save(staff);
    }
}
