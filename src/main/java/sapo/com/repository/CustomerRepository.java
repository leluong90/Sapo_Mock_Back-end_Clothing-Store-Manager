package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapo.com.model.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer , Long> {
}
