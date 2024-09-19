package sapo.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import sapo.com.model.entity.Customer;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>, CrudRepository<Customer, Integer> {

    public List<Customer> findAll();
    @Query("SELECT c FROM Customer c WHERE c.name LIKE %?1% OR c.phoneNumber LIKE %?1%")
    public Page<Customer> findByKeyword(String keyword, Pageable pageable);

    public Customer findByPhoneNumber(String phoneNumber);

}
