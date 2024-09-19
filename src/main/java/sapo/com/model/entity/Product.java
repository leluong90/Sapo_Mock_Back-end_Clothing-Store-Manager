package sapo.com.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id", nullable = false)  // Foreign key to Category table
    private Category category;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    private String description;
    @Column(name = "total_quantity")
    private Long totalQuantity;
    private Boolean status ;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private Set<ImagePath> imagePath;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn ;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_on")
    private LocalDateTime updatedOn ;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private Set<Variant> variants;

    public void addImagePath(ImagePath imagePath) {
        this.imagePath.add(imagePath);
        imagePath.setProduct(this);  // Set the reference to the product
    }

    public void setImagePaths(Set<ImagePath> imagePaths) {
        this.imagePath= imagePaths;
        for (ImagePath imagePath : imagePaths) {
            imagePath.setProduct(this);  // Set product reference in each image path
        }
    }

    public Set<String> getImagePaths(){
        Set<String> imagePaths= new HashSet<>();
        for(ImagePath imagePath: this.imagePath){
            imagePaths.add(imagePath.getPath());
        }
        return imagePaths;
    }
}