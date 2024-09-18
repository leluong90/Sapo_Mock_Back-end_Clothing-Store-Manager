package sapo.com.controller.product;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.ProductRequest;
import sapo.com.model.dto.request.VariantRequest;
import sapo.com.model.dto.response.ProductResponse;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.model.dto.response.VariantResponse;
import sapo.com.service.impl.ProductServiceImpl;

import java.util.Set;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductServiceImpl productService ;

    @GetMapping
    public ResponseEntity<?> getListOfProducts(@RequestParam Long page,@RequestParam Long limit,@RequestParam String query){
        try{
            Set<ProductResponse> productResponse = productService.getListOfProducts(page,limit,query);
            return new ResponseEntity<>(new ResponseObject("Get product info successfully", productResponse), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @GetMapping("/variants/{id}")
    public ResponseEntity<?> getListOfVariants(@RequestParam Long page,@RequestParam Long limit,@RequestParam String query){
        try{
            Set<VariantResponse> variantResponse = productService.getListOfVariants(page,limit,query);
            return new ResponseEntity<>(new ResponseObject("Get product info successfully", variantResponse), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewProduct(@Valid @RequestBody ProductRequest productRequest) {
        try{
            ProductResponse productResponse = productService.createNewProduct(productRequest);
            return new ResponseEntity<>(new ResponseObject("Create new product successfully",productResponse), HttpStatus.CREATED);
        }catch(Exception ex){
            log.error("error",ex);
            return null;
        }
    }

    @PostMapping("/{productId}/variants/create")
    public ResponseEntity<ResponseObject> createNewVariant(@PathVariable Long productId,@Valid @RequestBody VariantRequest variantRequest) {
        try{
            VariantResponse variantResponse = productService.createNewVariant(productId,variantRequest);
            return new ResponseEntity<>(new ResponseObject("Create new variant successfully",variantResponse), HttpStatus.CREATED);
        }catch(Exception ex){
            log.error("error",ex);
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            ProductResponse productResponse = productService.getProductById(id);
            return new ResponseEntity<>(new ResponseObject("Get product info successfully", productResponse), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @GetMapping("/{productId}/variants/{variantId}")
    public ResponseEntity<?> getVariantById(@PathVariable Long productId, @PathVariable Long variantId){
        try{
            VariantResponse variantResponse = productService.getVariantById(productId, variantId);
            return new ResponseEntity<>(new ResponseObject("Get variant info successfully", variantResponse), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequest productRequest) {

        try{
            ProductResponse productResponse = productService.updateProduct(id, productRequest);
            return new ResponseEntity<>(new ResponseObject("Product updated successfully", productResponse), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        try{
            Boolean checkk = productService.deleteProductById(id);
            if(checkk)
                return new ResponseEntity<>("Delete product successfully", HttpStatus.OK);
            else return new ResponseEntity<>("Fail to delete product", HttpStatus.BAD_REQUEST);
        }catch( Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @DeleteMapping("/{productId}/variants/{variantId}")
    public ResponseEntity<?> deleteVariantById(@PathVariable Long productId, @PathVariable Long variantId){
        try{
            Boolean checkk = productService.deleteVariantById(productId, variantId);
            if(checkk)
            return new ResponseEntity<>("Delete variant successfully", HttpStatus.OK);
            else return new ResponseEntity<>("Fail to delete variant", HttpStatus.BAD_REQUEST);
        }catch( Exception e){
            log.error("Error: ",e);
            return null;
        }
    }
}
