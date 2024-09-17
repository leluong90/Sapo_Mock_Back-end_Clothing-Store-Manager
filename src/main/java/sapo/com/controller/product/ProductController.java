package sapo.com.controller.product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.ProductRequest;
import sapo.com.model.dto.request.UserRequest;
import sapo.com.model.dto.response.ProductResponse;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.model.dto.response.UserResponse;
import sapo.com.model.dto.response.VariantResponse;
import sapo.com.model.entity.User;
import sapo.com.service.ProductService;
import sapo.com.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService ;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewProduct(@Valid @RequestBody ProductRequest productRequest) {

            ProductResponse productResponse = productService.createNewProduct(productRequest);
            System.out.println(productResponse);
            return new ResponseEntity<>(new ResponseObject("Product added successfully", productResponse), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        ProductResponse productResponse= productService.getProductById(id);
        return new ResponseEntity<>(new ResponseObject("Get product info successfully",productResponse), HttpStatus.OK);
    }

    @GetMapping("/{productId}/variants/{variantId}")
    public ResponseEntity<?> getVariantById(@PathVariable Long productId, @PathVariable Long variantId){
        VariantResponse variantResponse= productService.getVariantById(productId, variantId);
        return new ResponseEntity<>(new ResponseObject("Get product info successfully",variantResponse), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequest productRequest) {

        ProductResponse productResponse = productService.updateProduct(id,productRequest);
        System.out.println(productResponse);
        return new ResponseEntity<>(new ResponseObject("Product added successfully", productResponse), HttpStatus.CREATED);

    }
}
