package sapo.com.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.com.exception.DataConflictException;
import sapo.com.exception.ResourceNotFoundException;
import sapo.com.model.dto.request.BrandRequest;
import sapo.com.model.dto.response.BrandResponse;
import sapo.com.model.entity.Brand;
import sapo.com.model.entity.Product;
import sapo.com.repository.BrandRepository;
import sapo.com.service.BrandService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class BrandServiceImpl implements BrandService {

    private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    private BrandRepository brandRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Set<BrandResponse> getListOfBrands(Long page, Long limit, String queryString) {
        Set<Brand> brands = brandRepository.getListOfBrands(page, limit, queryString);
        Set<BrandResponse> brandsResponse = new HashSet<>();
        for (Brand brand : brands) {
            brandsResponse.add(brand.transferToResponse());
        }
        if(!brandsResponse.isEmpty())
            return brandsResponse;
        else throw new ResourceNotFoundException("Nhãn hiệu không tồn tại");
    }

    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhãn hiệu không tồn tại hoặc đã bị xóa"));
        if (brand.getStatus()) {
            return brand.transferToResponse();
        } else
            throw new ResourceNotFoundException("Nhãn hiệu không tồn tại hoặc đã bị xóa");
    }

    @Transactional
    public BrandResponse createNewBrand(BrandRequest brandRequest) {
        String code = brandRequest.getCode();
        if (code != "" && code.startsWith("PBN")) {
            throw new DataConflictException("Mã nhãn hiệu không được có tiền tố " + "PBN");
        }
        if (code != "" && brandRepository.existsByCode(code)) {
            throw new DataConflictException("Code " + code + " đã tồn tại.");
        }
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setCode(brandRequest.getCode());
        brand.setDescription(brandRequest.getDescription());
        brand.setStatus(true);
        brand.setCreatedOn(LocalDateTime.now());
        brand.setUpdatedOn(LocalDateTime.now());
        Brand savedBrand = brandRepository.save(brand);
        entityManager.refresh(savedBrand);
        return savedBrand.transferToResponse();
    }

    @Transactional
    public BrandResponse updateBrand(Long id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhãn hiệu không tồn tại hoặc đã bị xóa"));
        String code = brandRequest.getCode();
        if(code.equals(brand.getCode()) && code != ""){
            if (code.startsWith("PBN")) {
                throw new DataConflictException("Mã nhãn hiệu không được có tiền tố " + "PBN");
            }
            if (brandRepository.existsByCode(code)) {
                throw new DataConflictException("Code " + code + " đã tồn tại.");
            }
        }
        brand.setName(brandRequest.getName());
        if (code != "") {
            brand.setCode(code);
        }
        brand.setDescription(brandRequest.getDescription());
        brand.setUpdatedOn(LocalDateTime.now());
        Brand savedBrand = brandRepository.saveAndFlush(brand);
        entityManager.refresh(savedBrand);
        return savedBrand.transferToResponse();

    }

    @Transactional
    public Boolean deleteBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhãn hiệu không tồn tại hoặc đã bị xóa"));
        Set<Product> products= brandRepository.existProduct(id);
        if(products.isEmpty()){
            brandRepository.deleteBrandById(id);
            return true;
        }
        return false;
    }

}
