package sapo.com.service;

import sapo.com.model.dto.request.ProductRequest;
import sapo.com.model.dto.request.VariantRequest;
import sapo.com.model.dto.response.ProductResponse;
import sapo.com.model.dto.response.VariantResponse;

import java.util.Set;

public interface ProductService {
     Set<ProductResponse> getListOfProducts(Long page, Long limit,String queryString);
     Set<VariantResponse> getListOfVariants(Long page, Long limit, String queryString);
     ProductResponse getProductById(Long id);
     VariantResponse getVariantById(Long productId, Long variantId);
     ProductResponse createNewProduct(ProductRequest productRequest);
     VariantResponse createNewVariant(Long productId,VariantRequest variantRequest );
     ProductResponse updateProduct(Long id,ProductRequest productRequest);
     Boolean deleteProductById(Long id);
     Boolean deleteVariantById(Long productId, Long variantId);
}
