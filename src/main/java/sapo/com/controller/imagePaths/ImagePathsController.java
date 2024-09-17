package sapo.com.controller.imagePaths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.model.dto.request.CategoriesRequest;
import sapo.com.model.dto.request.ImagePathsRequest;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.model.entity.Categories;
import sapo.com.model.entity.ImagePaths;
import sapo.com.service.ImagePathsService;

import java.util.Optional;

@RestController
@RequestMapping("/v1/image_paths")
public class ImagePathsController {
    @Autowired
    private ImagePathsService imagePathsService ;
    @GetMapping()
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "10" , name = "limit") int limit ,
                                     @RequestParam(defaultValue = "0" , name = "page") int page ,
                                     @RequestParam(defaultValue = "products" , name = "sort") String sort ,
                                     @RequestParam(defaultValue = "asc" , name = "order") String order){
        Pageable pageable ;
        if (order.equals("asc")){
            pageable = PageRequest.of(page,limit , Sort.by(sort).ascending() );
        }else {
            pageable = PageRequest.of(page , limit , Sort.by(sort).descending() );
        }
        Page<ImagePaths> imagePaths = imagePathsService.findAll(pageable);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(imagePaths)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws Exception {
        Optional<ImagePaths> imagePath = imagePathsService.findById(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(imagePath)
                .build());


    }
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ImagePathsRequest imagePathsRequest) throws Exception {
        ImagePaths imagePath = imagePathsService.create(imagePathsRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.CREATED)
                .data(imagePath)

                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ImagePathsRequest imagePathsRequest , @PathVariable Integer id) throws Exception {
        ImagePaths imagePath = imagePathsService.update(imagePathsRequest,id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(imagePath)
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws Exception {
        imagePathsService.deleteById(id);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .build());
    }

}
