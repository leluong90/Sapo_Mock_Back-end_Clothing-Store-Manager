package sapo.com.model.dto.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sapo.com.model.entity.Brands;
import sapo.com.model.entity.Categories;
import sapo.com.model.entity.ImagePaths;
import sapo.com.model.entity.Variants;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ProductsRequest {
    private Categories categories ;
    private Brands brands ;
    private String name ;
    private String description ;
    private Integer totalQuantity ;
    private Boolean status ;


}
