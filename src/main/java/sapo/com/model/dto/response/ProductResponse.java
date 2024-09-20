package sapo.com.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sapo.com.model.entity.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ProductResponse {
    private Long id ;
    private String name;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private String description;
    private Long totalQuantity;
    private Boolean status ;
    private Set<String> size;
    private Set<String> color;
    private Set<String> material;
    private Set<String> imagePath;
    private LocalDateTime createdOn ;
    private LocalDateTime updatedOn ;
    private Set<VariantResponse> variants;

//    public ProductResponse(Product product) {
//        this.id = product.getId();
//        this.name = product.getName();
//        this.categoryId = product.getCategory().getId();
//        this.categoryName = product.getCategory().getName();
//        this.brandId = product.getBrand().getId();
//        this.brandName = product.getBrand().getName();
//        this.description = product.getDescription();
//        this.totalQuantity = product.getTotalQuantity();
//        this.status = product.getStatus();
//        this.imagePath = product.getImagePaths();
//        this.createdOn = product.getCreatedOn();
//        this.updateOn = product.getUpdateOn();
//        this.variants = product.getVariantRespone();
//    }

    public ProductResponse(){

    }
}
