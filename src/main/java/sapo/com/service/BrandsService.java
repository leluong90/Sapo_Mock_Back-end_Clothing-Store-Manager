package sapo.com.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sapo.com.model.dto.request.BrandsRequest;
import sapo.com.model.dto.request.CategoriesRequest;
import sapo.com.model.entity.Brands;
import sapo.com.model.entity.Categories;

import java.util.Optional;

public interface BrandsService {
    Page<Brands> findAll(Pageable pageable);
    Optional<Brands> findById(Integer id) throws Exception;
    Brands findByCode(String code) throws Exception;
    Brands findByName(String name) throws Exception;
    Brands create (BrandsRequest brandsRequest) throws Exception;
    Brands update (BrandsRequest brandsRequest , Integer id) throws Exception;
    void deleteById (Integer id) throws Exception;
}
