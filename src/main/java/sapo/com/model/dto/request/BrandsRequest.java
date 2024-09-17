package sapo.com.model.dto.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sapo.com.model.entity.Products;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class BrandsRequest {
//    private Set<Products> products ;
    private String code ;
    private String name ;
    private String description ;
}
