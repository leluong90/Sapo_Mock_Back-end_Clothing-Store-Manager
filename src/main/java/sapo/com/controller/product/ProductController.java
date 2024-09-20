package sapo.com.controller.product;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.exception.DataConflictException;
import sapo.com.exception.ResourceNotFoundException;
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

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<?> getListOfProducts(@RequestParam Long page, @RequestParam Long limit, @RequestParam String query) {
        Set<ProductResponse> productResponse = productService.getListOfProducts(page, limit, query);
        return new ResponseEntity<>(new ResponseObject("Lấy danh sách sản phẩm thành công", productResponse), HttpStatus.OK);
    }

    @GetMapping("/variants/{id}")
    public ResponseEntity<?> getListOfVariants(@RequestParam Long page, @RequestParam Long limit, @RequestParam String query) {
        Set<VariantResponse> variantResponse = productService.getListOfVariants(page, limit, query);
        return new ResponseEntity<>(new ResponseObject("Lấy danh sách phiên bản thành công", variantResponse), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return new ResponseEntity<>(new ResponseObject("Lấy thông tin sản phẩm thành công", productResponse), HttpStatus.OK);
    }

    @GetMapping("/{productId}/variants/{variantId}")
    public ResponseEntity<?> getVariantById(@PathVariable Long productId, @PathVariable Long variantId) {
        VariantResponse variantResponse = productService.getVariantById(productId, variantId);
        return new ResponseEntity<>(new ResponseObject("Lấy thông tin phiên bản thành công", variantResponse), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createNewProduct(productRequest);
        return new ResponseEntity<>(new ResponseObject("Tạo sản phẩm mới thành công", productResponse), HttpStatus.CREATED);
    }

    @PostMapping("/{productId}/variants/create")
    public ResponseEntity<ResponseObject> createNewVariant(@PathVariable Long productId, @Valid @RequestBody VariantRequest variantRequest) {
        VariantResponse variantResponse = productService.createNewVariant(productId, variantRequest);
        return new ResponseEntity<>(new ResponseObject("Tạo phiên bản mới thành công", variantResponse), HttpStatus.CREATED);

    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {

        ProductResponse productResponse = productService.updateProduct(id, productRequest);
        return new ResponseEntity<>(new ResponseObject("Cập nhật thông tin sản phẩm thành công", productResponse), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        Boolean checkk = productService.deleteProductById(id);
        return new ResponseEntity<>("Xóa sản phẩm thành công", HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/variants/{variantId}")
    public ResponseEntity<?> deleteVariantById(@PathVariable Long productId, @PathVariable Long variantId) {
        Boolean checkk = productService.deleteVariantById(productId, variantId);
        return new ResponseEntity<>("Xóa phiên bản thành công", HttpStatus.OK);
    }
}
