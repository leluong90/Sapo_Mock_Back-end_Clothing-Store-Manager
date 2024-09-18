package sapo.com.controller.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.CategoryRequest;
import sapo.com.model.dto.response.CategoryResponse;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.model.entity.Category;
import sapo.com.service.impl.CategoryServiceImpl;
import sapo.com.service.impl.ProductServiceImpl;

import java.util.Set;

@RestController
@RequestMapping("/v1/products/categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryServiceImpl categoryService ;

    @GetMapping
    public ResponseEntity<ResponseObject> getListOfCategories(@RequestParam Long page, @RequestParam Long limit, @RequestParam String query){
        try{
            Set<CategoryResponse> categories = categoryService.getListOfCategories(page,limit,query);
            return new ResponseEntity<>(new ResponseObject("Get categories info successfully", categories), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategoryById(@PathVariable Long id){
        try{
            CategoryResponse category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(new ResponseObject("Get category info successfully", category), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id,@RequestBody CategoryRequest categoryRequest){
        try{
            CategoryResponse category = categoryService.updateCategory(id,categoryRequest);
            return new ResponseEntity<>(new ResponseObject("Update category info successfully", category), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewCategory(@RequestBody CategoryRequest categoryRequest){
        try{
            CategoryResponse category = categoryService.createNewCategory(categoryRequest);
            return new ResponseEntity<>(new ResponseObject("Create new category successfully", category), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id){
        try{
            Boolean checkk = categoryService.deleteCategoryById(id);
            if(checkk)
                return new ResponseEntity<>("Delete category successfully", HttpStatus.OK);
            else
                return new ResponseEntity<>("Fail to delete category", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

}
