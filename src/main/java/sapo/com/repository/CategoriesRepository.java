package sapo.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sapo.com.model.entity.Brands;
import sapo.com.model.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories , Integer> {
    @Query("select c from Categories c where c.code = :code")
    Categories findByCode(String code);
    @Query("select c from Categories c where c.name = :name")
    Categories findByName (String name);
}
