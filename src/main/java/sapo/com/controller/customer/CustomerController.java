package sapo.com.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sapo.com.exception.CustomerNotFoundException;
import sapo.com.model.entity.Customer;
import sapo.com.service.CustomerService;

@RestController
public class CustomerController {

    @Autowired private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<Page<Customer>> findByKeyword(
            @RequestParam(value="pageNum", required = false, defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword) throws CustomerNotFoundException {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Customer> customers = customerService.findByKeyword(keyword, pageable);
        return new ResponseEntity<>(customers, HttpStatus.OK); // Trả về trạng thái 200 OK nếu thành công
    }

//    @GetMapping("/customers")
//    public ResponseEntity<?> findByPhoneNumber(@RequestParam String phoneNumber) {
//        Customer existingCustomer = customerService.findByPhoneNumber(phoneNumber);
//        if (existingCustomer == null) {
//            return new ResponseEntity<>("Không tìm thấy khách hàng ứng với SĐT: " + phoneNumber, HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
//    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            Customer customer = customerService.findById(id);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/customers/create")
    public ResponseEntity<?> createCustomer(@Validated @RequestBody Customer customer) {
        try {
            // Check if the phone number already exists
            Customer existingCustomer = customerService.findByPhoneNumber(customer.getPhone());
            if (existingCustomer != null) {
                return new ResponseEntity<>("Phone number already exists.", HttpStatus.CONFLICT);
            }

            // Save the new customer
            customerService.register(customer);
            return new ResponseEntity<>("Create new customer successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("/customers/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id,
                     @RequestBody Customer customerInForm) throws CustomerNotFoundException {
        Customer customerInDb = customerService.findById(id);
        customerInForm.setId(id);
        customerService.update(customerInForm);
        return new ResponseEntity<>("Customer ID:"+id+" updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/customers/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long customerId) throws CustomerNotFoundException {

            customerService.delete(customerId);
            return new ResponseEntity<>("Customer with ID " + customerId + " has been successfully deleted.",HttpStatus.OK);


    }





}
