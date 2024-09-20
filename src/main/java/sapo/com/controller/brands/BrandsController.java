package sapo.com.controller.brands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.BrandsRequest;
import sapo.com.model.dto.request.CategoriesRequest;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.model.entity.Brands;
import sapo.com.model.entity.Categories;
import sapo.com.service.BrandsService;

import java.util.Optional;

@RestController
@RequestMapping("/v1/brands")
public class BrandsController {
    @Autowired
    private BrandsService brandsService;
    @GetMapping()
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "10" , name = "limit") int limit ,
                                     @RequestParam(defaultValue = "0" , name = "page") int page ,
                                     @RequestParam(defaultValue = "name" , name = "sort") String sort ,
                                     @RequestParam(defaultValue = "asc" , name = "order") String order){
        Pageable pageable ;
        if (order.equals("asc")){
            pageable = PageRequest.of(page,limit , Sort.by(sort).ascending() );
        }else {
            pageable = PageRequest.of(page , limit , Sort.by(sort).descending() );
        }
        Page<Brands> brands = brandsService.findAll(pageable);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("successfully")
                        .status(HttpStatus.OK)
                        .data(brands)
                .build());


    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws Exception {
        Optional<Brands> brand = brandsService.findById(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(brand)
                .build());


    }
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody BrandsRequest brandsRequest) throws Exception {
        Brands brand = brandsService.create(brandsRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.CREATED)
                .data(brand)

                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody BrandsRequest brandsRequest , @PathVariable Integer id) throws Exception {
        Brands brand = brandsService.update(brandsRequest,id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(brand)
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws Exception {
        brandsService.deleteById(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .build());
    }
}
