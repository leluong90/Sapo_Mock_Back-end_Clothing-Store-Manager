package sapo.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import sapo.com.model.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer , Long>, PagingAndSortingRepository<Customer, Long>, CrudRepository<Customer, Long> {
    public List<Customer> findAll();
    @Query(value = "SELECT * FROM customers c WHERE c.name LIKE %?1% OR c.phone_number LIKE %?1%", nativeQuery = true)
    public Page<Customer> findByKeyword(String keyword, Pageable pageable);

    public Customer findByPhoneNumber(String phoneNumber);
}
