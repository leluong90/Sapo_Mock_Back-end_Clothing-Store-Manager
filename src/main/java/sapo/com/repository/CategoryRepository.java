package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.com.model.entity.Category;
import sapo.com.model.entity.Product;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
