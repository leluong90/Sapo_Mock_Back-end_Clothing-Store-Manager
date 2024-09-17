package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.com.model.entity.Brand;
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
