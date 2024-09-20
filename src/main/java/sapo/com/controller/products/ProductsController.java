package sapo.com.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.ProductsRequest;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.model.entity.Products;
import sapo.com.service.ProductsService;

import java.util.Optional;

@RestController
@RequestMapping("/v1/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService ;
    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "10" ,name = "limit") int limit ,
                                     @RequestParam(defaultValue = "0" , name ="page" ) int page ,
                                     @RequestParam(defaultValue = "name" , name = "sort") String sort ,
                                     @RequestParam(defaultValue = "asc" , name = "order") String order){
        Pageable pageable ;
        if (order.equals("asc")){
            pageable = PageRequest.of(page,limit , Sort.by(sort).ascending() );
        }else {
            pageable = PageRequest.of(page , limit , Sort.by(sort).descending() );
        }
        Page<Products> products = productsService.findAll(pageable);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.OK)
                        .data(products)
                .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) throws Exception {
        Optional<Products> product = productsService.findById(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.OK)
                        .data(product)
                .build());
    }

    @PostMapping("")
    public ResponseEntity<?> create (@RequestBody ProductsRequest productsRequest) throws Exception {
        Products product = productsService.create(productsRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.CREATED)
                        .data(product)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@RequestBody ProductsRequest productsRequest , @PathVariable("id") Integer id) throws Exception {
        Products product = productsService.update(productsRequest, id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.OK)
                        .data(product)
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") Integer id) throws Exception {
        productsService.delete(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.OK)
                .build());
    }

}
