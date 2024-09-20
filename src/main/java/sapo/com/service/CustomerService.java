package sapo.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sapo.com.exception.CustomerNotFoundException;
import sapo.com.model.entity.Customer;
import sapo.com.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired private CustomerRepository customerRepository;

    public Page<Customer> findByKeyword(String keyword, Pageable pageable) throws CustomerNotFoundException  {
        Page<Customer> customersByKeyword;
        if (keyword != null) {
            customersByKeyword = customerRepository.findByKeyword(keyword, pageable);
        } else {
            List<Customer> customerList = customerRepository.findAll();
            customersByKeyword = new PageImpl<>(customerList, pageable, customerList.size());
        }


        // Kiểm tra nếu không tìm thấy khách hàng nào
        if (customersByKeyword.isEmpty()) {
            throw new CustomerNotFoundException("Không tìm thấy khách hàng nào.");
        }
        return customersByKeyword;



    }

    public Customer findByPhoneNumber(String phoneNumber) {

        Customer existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);
        return existingCustomer;


    }

    public Customer findById(Long id) throws CustomerNotFoundException{
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
    }

    public void register(Customer customer){
        customer.setCreatedOn(LocalDateTime.now());
        customer.setUpdatedOn(LocalDateTime.now());
        customerRepository.save(customer);

    }
    public void update(Customer customerInForm) throws CustomerNotFoundException {
        Optional<Customer> customerInDB = customerRepository.findById(customerInForm.getId());
        if(customerInDB.isPresent()){
            customerInForm.setCreatedOn(customerInDB.get().getCreatedOn());
            customerInForm.setUpdatedOn(LocalDateTime.now());
            customerRepository.save(customerInForm);
        }else{
            throw new CustomerNotFoundException("Customer not found with ID: " + customerInForm.getId());
        }
    }

    public void delete(Long id) throws CustomerNotFoundException{

        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            customerRepository.deleteById(id);

        }else{
            throw new CustomerNotFoundException("Không tìm thấy thông tin khách hàng ID:"+id);
        }

    }

}
