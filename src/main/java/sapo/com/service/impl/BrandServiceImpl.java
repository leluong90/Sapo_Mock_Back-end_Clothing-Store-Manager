package sapo.com.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.com.exception.ResourceNotFoundException;
import sapo.com.model.dto.request.BrandRequest;
import sapo.com.model.dto.request.CategoryRequest;
import sapo.com.model.dto.response.BrandResponse;
import sapo.com.model.dto.response.CategoryResponse;
import sapo.com.model.entity.Brand;
import sapo.com.model.entity.Category;
import sapo.com.repository.BrandRepository;
import sapo.com.repository.CategoryRepository;
import sapo.com.repository.ProductRepository;
import sapo.com.service.BrandService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandRepository brandRepository;


    public Set<BrandResponse> getListOfBrands(Long page, Long limit, String queryString){
        try{
            Set<Brand> brands = brandRepository.getListOfBrands(page,limit,queryString);
            Set<BrandResponse> brandsResponse = new HashSet<>();
            for(Brand brand: brands){
                brandsResponse.add(brand.transferToResponse());
            }
            return brandsResponse;
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    public BrandResponse getBrandById(Long id){
        try{
            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
//            if(category.getStatus()==false){
//                throw new ResourceNotFoundException("Category no longer existed");
//            }
            return brand.transferToResponse();
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    @Transactional
    public BrandResponse createNewBrand(BrandRequest brandRequest){
        try{
            Brand brand = new Brand();
            brand.setName(brandRequest.getName());
            brand.setCode(brandRequest.getCode());
            brand.setDescription(brandRequest.getDescription());
            brand.setStatus(true);
            brand.setCreatedOn(LocalDateTime.now());
            brand.setUpdatedOn(LocalDateTime.now());
            Brand savedBrand = brandRepository.save(brand);
            return savedBrand.transferToResponse();
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    @Transactional
    public BrandResponse updateBrand(Long id, BrandRequest brandRequest){
        try{
            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
            brand.setName(brandRequest.getName());
            brand.setCode(brandRequest.getCode());
            brand.setDescription(brandRequest.getDescription());
            brand.setUpdatedOn(LocalDateTime.now());
            Brand savedBrand = brandRepository.save(brand);
            return savedBrand.transferToResponse();
        }catch(Exception e){
            log.error("Error:",e);
            return null;
        }
    }

    @Transactional
    public Boolean deleteBrandById(Long id){
        try{
            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
            brandRepository.deleteBrandById(id);
            return true;
        }catch(Exception e){
            log.error("Error:",e);
            return false;
        }
    }
}
