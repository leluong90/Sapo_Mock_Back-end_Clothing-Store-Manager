package sapo.com.service;

import sapo.com.model.dto.request.BrandRequest;
import sapo.com.model.dto.response.BrandResponse;

import java.util.Set;

public interface BrandService {

    Set<BrandResponse> getListOfBrands(Long page, Long limit, String queryString);
    BrandResponse getBrandById(Long id);
    BrandResponse createNewBrand(BrandRequest brandRequest);
    BrandResponse updateBrand(Long id, BrandRequest brandRequest);
    Boolean deleteBrandById(Long id);

}
