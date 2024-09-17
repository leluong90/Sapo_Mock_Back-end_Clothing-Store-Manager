package sapo.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.com.exception.ResourceNotFoundException;
import sapo.com.exception.UserException;
import sapo.com.model.dto.request.ProductRequest;
import sapo.com.model.dto.request.VariantRequest;
import sapo.com.model.dto.response.ProductResponse;
import sapo.com.model.dto.response.VariantResponse;
import sapo.com.model.entity.*;
import sapo.com.repository.*;
import sapo.com.service.ProductService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl{
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

    public Set<ProductResponse> getListOfProducts(Long page, Long limit,String queryString){
        try{
            Set<Product> products = productRepository.getListOfProducts(page, limit, queryString);
            Set<ProductResponse> productsResponse = new HashSet<>();
            for(Product product: products){
                productsResponse.add(new ProductResponse(product));
            }
            return productsResponse;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<VariantResponse> getListOfVariants(Long page, Long limit,String queryString){
        try{
            Set<Variant> variants = variantRepository.getListOfVariants(page, limit, queryString);
            Set<VariantResponse> variantsResponse = new HashSet<>();
            for(Variant variant: variants){
                variantsResponse.add(new VariantResponse(variant));
            }
            return variantsResponse;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProductResponse createNewProduct(ProductRequest productRequest){

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand id not found"));

        // Create Product entity and set fields from ProductRequest
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setVariants(product.getVariants());
        product.setCategory(category);
        product.setBrand(brand);
        Set<ImagePath> newImages = new HashSet<>();
        for (String imagePath : productRequest.getImagePath()) {
            ImagePath img = new ImagePath();
            img.setProduct(product);
            img.setPath(imagePath);
            newImages.add(img);
        }
        product.setImagePath(newImages);
        // Save product in repository
        Product savedProduct = productRepository.save(product);
        ProductResponse productResponse= new ProductResponse(savedProduct);
        statisticSizeColorMaterial(productResponse);
        return productResponse;
    }

    public VariantResponse createNewVariant(Long productId,VariantRequest variantRequest ){

        Product product = productRepository.findById(variantRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));

        Variant newVariant = new Variant();
        newVariant.transferFromRequest(variantRequest);
        newVariant.setCreatedOn(LocalDateTime.now());

        Variant savedVariant = variantRepository.save(newVariant);
        VariantResponse variantResponse= new VariantResponse(savedVariant);

        return variantResponse;
    }

    public ProductResponse getProductById(Long id){

        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            ProductResponse productResponse= new ProductResponse(product.get());
            statisticSizeColorMaterial(productResponse);
            return productResponse;
        } else{
            throw new ResourceNotFoundException("Product id not found");
        }
    }

    public VariantResponse getVariantById(Long productId, Long variantId){

        Optional<Variant> variant = variantRepository.findById(variantId);
        if(variant.isPresent()){
            return new VariantResponse(variant.get());
        } else{
            throw new ResourceNotFoundException("Variant id not found");
        }
    }

    @Transactional
    public ProductResponse updateProduct(Long id,ProductRequest productRequest){
        Product product = productRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Product id not found"));
        if(product.getCategory().getId()!=productRequest.getCategoryId()){
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        }
        if(product.getBrand().getId()!=productRequest.getBrandId()){
            Brand brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand id not found"));
        }
        product.getVariants().clear();
        for(VariantRequest variantRequest: productRequest.getVariants()){
            Variant variant = variantRepository.findById(variantRequest.getId())
                    .orElseThrow(()-> new ResourceNotFoundException("Variant id not found"));
            variant.transferFromRequest(variantRequest);
            product.getVariants().add(variant);
        }
        // Create Product entity and set fields from ProductRequest
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setVariants(product.getVariants());
        imagePathRepository.deleteByProductId(id);
        Set<ImagePath> newImages = new HashSet<>();
        for (String imagePath : productRequest.getImagePath()) {
            ImagePath img = new ImagePath();
            img.setProduct(product);
            img.setPath(imagePath);
            newImages.add(img);
        }
        product.setImagePath(newImages);
        // Save product in repository
        Product savedProduct = productRepository.save(product);
        ProductResponse productResponse= new ProductResponse(savedProduct);
        statisticSizeColorMaterial(productResponse);
        return productResponse;
    }

    public void statisticSizeColorMaterial(ProductResponse productResponse){
        productResponse.setSize(variantRepository.findDistinctSizesByProductId(productResponse.getId()));
        productResponse.setColor(variantRepository.findDistinctColorsByProductId(productResponse.getId()));
        productResponse.setMaterial(variantRepository.findDistinctMaterialsByProductId(productResponse.getId()));
    }

    @Transactional
    public  Boolean deleteProductById(Long id){
        try{
            productRepository.deleteProductById(id);
            variantRepository.deleteAllVariantOfProduct(id);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public  Boolean deleteVariantById(Long id){
        try{
            variantRepository.deleteById(id);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
}
