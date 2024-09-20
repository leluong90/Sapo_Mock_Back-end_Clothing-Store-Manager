package sapo.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sapo.com.model.dto.request.CategoriesRequest;
import sapo.com.model.dto.request.ProductsRequest;
import sapo.com.model.entity.Categories;
import sapo.com.model.entity.Products;

import java.util.Optional;

public interface CategoriesService {
    Page<Categories> findAll(Pageable pageable);
    Optional<Categories> findById(Integer id) throws Exception;
    Categories findByCode (String code) throws Exception;
    Categories findByName (String name) throws Exception;
    Categories create (CategoriesRequest categoriesRequest) throws Exception;
    Categories update (CategoriesRequest categoriesRequest , Integer id) throws Exception;
    void deleteById (Integer id) throws Exception;
}
