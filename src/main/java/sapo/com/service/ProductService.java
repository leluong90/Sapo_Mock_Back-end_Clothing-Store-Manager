package sapo.com.service;

import sapo.com.exception.ProductException;
import sapo.com.model.dto.request.ProductRequest;
import sapo.com.model.dto.request.VariantRequest;
import sapo.com.model.dto.response.ProductResponse;
import sapo.com.model.entity.Product;
import sapo.com.model.entity.Variant;

public interface ProductService {
     Product findProductById(Long id);
     ProductResponse createNewProduct(ProductRequest productRequest);
     Product updateProduct(Long id,Product product);
     Product deleteProduct(Long id);
     Variant findVariantById(Long id);
     Variant createNewVariant(VariantRequest variantRequest);
     Variant updateVariant(Long id,Variant variant);
     Variant deleteVariant(Long id);

}
