package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sapo.com.model.entity.Brands;
import sapo.com.model.entity.Categories;

public interface BrandsRepository extends JpaRepository<Brands , Integer> {
    @Query("select b from Brands b where b.code = :code")
    Brands findByCode(String code);
    @Query("select b from Brands b where b.name = :name")
    Brands findByName (String name);
}
