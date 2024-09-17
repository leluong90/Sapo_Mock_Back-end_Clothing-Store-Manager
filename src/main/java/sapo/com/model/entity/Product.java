package sapo.com.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import sapo.com.model.dto.request.ProductRequest;
import sapo.com.model.dto.request.VariantRequest;
import sapo.com.model.dto.response.ProductResponse;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)  // Foreign key to Category table
    private Category category;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    private String description;
    @Column(name = "total_quantity")
    private Integer totalQuantity;
    private Boolean status ;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ImagePath> imagePath;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn ;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_on")
    private LocalDateTime updateOn ;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Variant> variants;

//    public void transferFromRequest(ProductResponse productResponse){
//        this.name=productResponse.getName();
//        this.description=productResponse.getDescription();
//        this.imagePath=productResponse.getImagePath();
//    }
}