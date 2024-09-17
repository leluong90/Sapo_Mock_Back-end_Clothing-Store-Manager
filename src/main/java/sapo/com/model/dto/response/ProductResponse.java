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
    private Integer totalQuantity;
    private Boolean status ;
    private Set<String> size;
    private Set<String> color;
    private Set<String> material;
    private Set<ImagePath> imagePath;
    private LocalDateTime createdOn ;
    private LocalDateTime updateOn ;
    private Set<Variant> variants;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();
        this.brandId = product.getBrand().getId();
        this.brandName = product.getBrand().getName();
        this.description = product.getDescription();
        this.totalQuantity = product.getTotalQuantity();
        this.status = product.getStatus();
        this.imagePath = product.getImagePath();
        this.createdOn = product.getCreatedOn();
        this.updateOn = product.getUpdateOn();
        this.variants = product.getVariants();
    }


}
