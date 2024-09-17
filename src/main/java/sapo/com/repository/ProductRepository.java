package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.com.model.entity.Product;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("update products p set p.status=0 where p.id = :id")
    Boolean deleteProductById(Long id);

    @Query(
            value = "CALL get_list_of_products(:page, :limit, :query)",
            nativeQuery = true
    )
    Set<Product> getListOfProducts(Long page, Long limit, String query );
}
