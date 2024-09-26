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
import sapo.com.model.dto.response.ResponseObject;
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
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> findById(@PathVariable Long customerId) throws CustomerNotFoundException {
        Customer existingCustomer = customerService.findById(customerId);
        if (existingCustomer == null) {
            return new ResponseEntity<>("Không tìm thấy khách hàng ứng với ID: " + customerId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
    }

    @PostMapping("/customers/create")
    public ResponseEntity<ResponseObject> createCustomer(@Validated @RequestBody Customer customer) {
        try {
            // Check if the phone number already exists
            Customer existingCustomer = customerService.findByPhoneNumber(customer.getPhone());
            if (existingCustomer != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        ResponseObject.builder()
                                .message("Phone number already exists.")
                                .status(HttpStatus.CONFLICT)
                                .data(null)
                                .build()
                );
            }

            // Save the new customer
            customerService.register(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ResponseObject.builder()
                            .message("Create new customer successfully")
                            .status(HttpStatus.CREATED)
                            .data(null)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ResponseObject.builder()
                            .message("Unauthorized")
                            .status(HttpStatus.UNAUTHORIZED)
                            .data(null)
                            .build()
            );
        }
    }
    @PutMapping("/customers/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id,
                                            @RequestBody Customer customerInForm) throws CustomerNotFoundException {
        Customer existingCustomer = customerService.findByPhoneNumber(customerInForm.getPhone());
        if (existingCustomer != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ResponseObject.builder()
                            .message("Phone number already exists.")
                            .status(HttpStatus.CONFLICT)
                            .data(null)
                            .build()
            );
        }
        Customer customerInDb = customerService.findById(id);
        customerInForm.setId(id);
        Customer updatedCustomer = customerService.update(customerInForm);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .message("Customer ID:"+id+" updated successfully")
                        .status(HttpStatus.OK)
                        .data(updatedCustomer)
                        .build()
        );
    }

    @DeleteMapping("/customers/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long customerId) throws CustomerNotFoundException {

        customerService.delete(customerId);
        return new ResponseEntity<>("Customer with ID " + customerId + " has been successfully deleted.",HttpStatus.OK);


    }





}
