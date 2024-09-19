package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapo.com.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
