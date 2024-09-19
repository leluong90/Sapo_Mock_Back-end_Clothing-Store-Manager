package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sapo.com.model.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
