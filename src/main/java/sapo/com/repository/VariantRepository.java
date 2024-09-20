package sapo.com.repository;

import org.aspectj.weaver.ast.Var;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sapo.com.model.entity.Product;
import sapo.com.model.entity.Variant;

import java.util.Set;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    @Query("SELECT DISTINCT v.size FROM Variant v WHERE v.product.id = :productId")
    Set<String> findDistinctSizesByProductId(@Param("productId") Long productId);

    @Query("SELECT DISTINCT v.color FROM Variant v WHERE v.product.id = :productId")
    Set<String> findDistinctColorsByProductId(@Param("productId") Long productId);

    @Query("SELECT DISTINCT v.material FROM Variant v WHERE v.product.id = :productId")
    Set<String> findDistinctMaterialsByProductId(@Param("productId") Long productId);

    @Modifying
    @Query("update Variant v set v.status = false where v.id = :id")
    int deleteVariantById(@Param("id") Long id);

    @Modifying
    @Query("update Variant v set v.status = false where v.product.id = :productId")
    int deleteAllVariantOfProduct(Long productId);

    @Query(
            value = "CALL get_list_of_variants(:page, :limit, :query)",
            nativeQuery = true
    )
    Set<Variant> getListOfVariants(Long page, Long limit, String query );


}