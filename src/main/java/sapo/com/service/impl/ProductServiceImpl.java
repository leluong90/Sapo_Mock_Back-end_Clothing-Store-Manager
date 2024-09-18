package sapo.com.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.com.exception.ResourceNotFoundException;
import sapo.com.model.dto.request.ProductRequest;
import sapo.com.model.dto.request.VariantRequest;
import sapo.com.model.dto.response.ProductResponse;
import sapo.com.model.dto.response.VariantResponse;
import sapo.com.model.entity.*;
import sapo.com.repository.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl{

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ImagePathRepository imagePathRepository;

    @Transactional
    public Set<ProductResponse> getListOfProducts(Long page, Long limit,String queryString){
        try{
            Set<Product> products = productRepository.getListOfProducts(page, limit, queryString);
            Set<ProductResponse> productsResponse = new HashSet<>();
            for(Product product: products){
                productsResponse.add(product.transferToResponse());
            }
            return productsResponse;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Set<VariantResponse> getListOfVariants(Long page, Long limit,String queryString){
        try{
            Set<Variant> variants = variantRepository.getListOfVariants(page, limit, queryString);
            Set<VariantResponse> variantsResponse = new HashSet<>();
            for(Variant variant: variants){
                variantsResponse.add(variant.transferToResponse());
            }
            return variantsResponse;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public ProductResponse createNewProduct(ProductRequest productRequest){
        try{
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            Brand brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand id not found"));

            // Map ProductRequest to Product entity
            Product product= productRequest.transferToProduct();
            product.setBrand(brand);
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);
            entityManager.refresh(savedProduct);
            ProductResponse productResponse = savedProduct.transferToResponse();
            statisticSizeColorMaterial(productResponse);
            return productResponse;
        }catch(Exception e){
            log.error("error",e);
            return null;
        }
    }
    @Transactional
    public VariantResponse createNewVariant(Long productId,VariantRequest variantRequest ){

        try{

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
//        product.getVariants().add(variantRequest.transferToVariant());
//            product.setUpdatedOn(LocalDateTime.now());
            variantRequest.setProductId(productId);
            Variant variant= variantRequest.transferToVariant();
            variant.setProduct(product);
            Variant savedVariant = variantRepository.save(variant);
            product.setUpdatedOn(LocalDateTime.now());
            productRepository.save(product);
            entityManager.refresh(savedVariant);
            return savedVariant.transferToResponse();
        }catch(Exception e){
            log.error("error",e);
            return null;
        }
    }

    public ProductResponse getProductById(Long id){
        try{
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                ProductResponse productResponse = product.get().transferToResponse();
                statisticSizeColorMaterial(productResponse);
                return productResponse;
            } else {
                throw new ResourceNotFoundException("Product id not found");
            }
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    public VariantResponse getVariantById(Long productId, Long variantId){
        try{
            Optional<Variant> variant = variantRepository.findById(variantId);
            if (variant.isPresent()) {
                return variant.get().transferToResponse();
            } else {
                throw new ResourceNotFoundException("Variant id not found");
            }
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @Transactional
    public ProductResponse updateProduct(Long id,ProductRequest productRequest){
        try{
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            Brand brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand id not found"));
            System.out.println(product.getStatus());
            product.setBrand(brand);
            product.setCategory(category);
            product.setDescription(productRequest.getDescription());
            product.setName(productRequest.getName());
            Map<Long, VariantRequest> existingVariantsMap = productRequest.getVariants().stream()
                    .collect(Collectors.toMap(VariantRequest::getId, Function.identity()));
            for (Variant variant : product.getVariants()) {
                variant.updateFromRequest(existingVariantsMap.get(variant.getId()));
            }

            product.getImagePath().clear();
            for (String imagePath : productRequest.getImagePath()) {
                ImagePath image = new ImagePath();
                image.setPath(imagePath);
                image.setProduct(product);
                product.getImagePath().add(image);
            }
            product.setUpdatedOn(LocalDateTime.now());
            Product savedProduct = productRepository.saveAndFlush(product);
            entityManager.refresh(savedProduct);
            ProductResponse productResponse = savedProduct.transferToResponse();
            statisticSizeColorMaterial(productResponse);
            return productResponse;
        }catch(Exception e){
            log.error("error",e);
            return null;
        }
    }

    public void statisticSizeColorMaterial(ProductResponse productResponse){
        productResponse.setSize(variantRepository.findDistinctSizesByProductId(productResponse.getId()));
        productResponse.setColor(variantRepository.findDistinctColorsByProductId(productResponse.getId()));
        productResponse.setMaterial(variantRepository.findDistinctMaterialsByProductId(productResponse.getId()));
    }

    @Transactional
    public  Boolean deleteProductById(Long id){
        try{
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
            productRepository.deleteProductById(id);
            variantRepository.deleteAllVariantOfProduct(id);
            return true;
        }catch(Exception ex){
            log.error("error ",ex);
            return false;
        }
    }

    @Transactional
    public  Boolean deleteVariantById(Long productId, Long variantId){
        try{
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
            Variant variant = variantRepository.findById(variantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
            variantRepository.deleteVariantById(variantId);
            return true;
        }catch(Exception ex){
            log.error("error ",ex);
            return false;
        }
    }
}
