package sapo.com.controller.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.BrandRequest;
import sapo.com.model.dto.request.CategoryRequest;
import sapo.com.model.dto.response.BrandResponse;
import sapo.com.model.dto.response.CategoryResponse;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.service.impl.BrandServiceImpl;
import sapo.com.service.impl.CategoryServiceImpl;

import java.util.Set;

@RestController
@RequestMapping("/v1/products/brands")
public class BrandController {

    private static final Logger log = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandServiceImpl brandService ;

    @GetMapping
    public ResponseEntity<ResponseObject> getListOfBrands(@RequestParam Long page, @RequestParam Long limit, @RequestParam String query){
        try{
            Set<BrandResponse> brands = brandService.getListOfBrands(page,limit,query);
            return new ResponseEntity<>(new ResponseObject("Get brands info successfully", brands), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBrandById(@PathVariable Long id){
        try{
            BrandResponse brand = brandService.getBrandById(id);
            return new ResponseEntity<>(new ResponseObject("Get brand info successfully", brand), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBrand(@PathVariable Long id,@RequestBody BrandRequest brandRequest){
        try{
            BrandResponse brand = brandService.updateBrand(id,brandRequest);
            return new ResponseEntity<>(new ResponseObject("Update brand info successfully", brand), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewBrand(@RequestBody BrandRequest brandRequest){
        try{
            BrandResponse brand = brandService.createNewBrand(brandRequest);
            return new ResponseEntity<>(new ResponseObject("Create new brand successfully", brand), HttpStatus.OK);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable Long id){
        try{
            Boolean checkk = brandService.deleteBrandById(id);
            if(checkk)
                return new ResponseEntity<>("Delete brand successfully", HttpStatus.OK);
            else
                return new ResponseEntity<>("Fail to delete brand", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            log.error("Error: ",e);
            return null;
        }
    }

}
