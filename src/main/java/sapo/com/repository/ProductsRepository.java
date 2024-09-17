package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sapo.com.model.entity.Products;

public interface ProductsRepository extends JpaRepository<Products , Integer> {
    @Query("select p from Products p where p.name = :name")
    Products findByName (String name);
}
