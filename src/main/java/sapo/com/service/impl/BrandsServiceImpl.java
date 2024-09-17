package sapo.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sapo.com.model.dto.request.BrandsRequest;
import sapo.com.model.entity.Brands;

import sapo.com.repository.BrandsRepository;
import sapo.com.service.BrandsService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BrandsServiceImpl implements BrandsService {
    @Autowired
    private BrandsRepository brandsRepository ;
    @Override
    public Page<Brands> findAll(Pageable pageable) {
        return brandsRepository.findAll(pageable);
    }

    @Override
    public Optional<Brands> findById(Integer id) throws Exception {
        Optional<Brands> brand = brandsRepository.findById(id);
        if (brand.isPresent()){
            return brand ;
        }else {
            throw new Exception("Id not found");
        }

    }

    @Override
    public Brands findByCode(String code) throws Exception {
        Brands brand = brandsRepository.findByCode(code);
        if (brand != null){
            return brand;
        }else {
            throw new Exception("Code not found");
        }
    }

    @Override
    public Brands findByName(String name) throws Exception {
        Brands brand = brandsRepository.findByName(name);
        if (brand != null){
            return brand;
        }else {
            throw new Exception("Name not found");
        }
    }

    @Override
    public Brands create(BrandsRequest brandsRequest) throws Exception {
        if (brandsRepository.findByCode(brandsRequest.getCode()) == null){

            Brands brand = Brands.builder()
                    .code(brandsRequest.getCode())
                    .name(brandsRequest.getName())
                    .description(brandsRequest.getDescription())
                    .createdOn(LocalDateTime.now())
                    .build();
            return brandsRepository.save(brand);
        }else {
            throw new Exception("Exist Brand");
        }

    }

    @Override
    public Brands update(BrandsRequest brandsRequest, Integer id) throws Exception {
        Optional<Brands> brand = brandsRepository.findById(id);
        if (brand.isPresent()){
            Brands updateBrand = brand.get();
            updateBrand.setCode(brandsRequest.getCode());
            updateBrand.setName(brandsRequest.getName());
            updateBrand.setDescription(brandsRequest.getDescription());
            updateBrand.setUpdateOn(LocalDateTime.now());
            return brandsRepository.save(updateBrand);
        }else {
            throw new Exception("Id not found");
        }

    }

    @Override
    public void deleteById(Integer id) throws Exception {
        Optional<Brands> brands = brandsRepository.findById(id);
        if (brands.isPresent()){
            brandsRepository.deleteById(id);
        }else {
            throw new Exception("Id not found");
        }

    }
}
