package sapo.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sapo.com.model.dto.request.ProductsRequest;
import sapo.com.model.entity.Products;

import java.util.Optional;

public interface ProductsService {
    Page<Products> findAll(Pageable pageable);
    Optional<Products> findById(Integer id) throws Exception;
    Products findByName (String name) throws Exception;
    Products create (ProductsRequest productsRequest) throws Exception;
    Products update (ProductsRequest productsRequest , Integer id) throws Exception;
    void delete (Integer id) throws Exception;
}
