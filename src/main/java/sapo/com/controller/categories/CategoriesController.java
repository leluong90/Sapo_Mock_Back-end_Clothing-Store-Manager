package sapo.com.controller.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.CategoriesRequest;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.model.entity.Categories;
import sapo.com.model.entity.Products;
import sapo.com.service.CategoriesService;

import java.util.Optional;

@RestController
@RequestMapping("/v1/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService ;
    @GetMapping("")
    public ResponseEntity<?> findAll (@RequestParam(defaultValue = "10" , name = "limit") int limit ,
                                      @RequestParam(defaultValue = "0" , name = "page") int page ,
                                      @RequestParam(defaultValue = "name" , name = "sort") String sort ,
                                      @RequestParam(defaultValue = "asc" , name = "order") String order){
        Pageable pageable ;
        if (order.equals("asc")){
            pageable = PageRequest.of(page,limit , Sort.by(sort).ascending() );
        }else {
            pageable = PageRequest.of(page , limit , Sort.by(sort).descending() );
        }
        Page<Categories> categories = categoriesService.findAll(pageable);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(categories)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws Exception {
        Optional<Categories> category = categoriesService.findById(id);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Successfully")
                    .status(HttpStatus.OK)
                    .data(category)
                    .build());


    }
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoriesRequest categoriesRequest) throws Exception {
        Categories category = categoriesService.create(categoriesRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.CREATED)
                        .data(category)

                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CategoriesRequest categoriesRequest , @PathVariable Integer id) throws Exception {
        Categories category = categoriesService.update(categoriesRequest,id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.OK)
                        .data(category)
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws Exception {
        categoriesService.deleteById(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Successfully")
                        .status(HttpStatus.OK)
                .build());
    }
}
