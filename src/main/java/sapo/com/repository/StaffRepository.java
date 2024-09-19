package sapo.com.repository;

import org.springframework.data.repository.CrudRepository;
import sapo.com.model.entity.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
}
