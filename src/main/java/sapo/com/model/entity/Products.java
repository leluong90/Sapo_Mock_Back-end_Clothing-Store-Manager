package sapo.com.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Categories categories ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brands brands ;

    @OneToMany(fetch = FetchType.EAGER , mappedBy = "products" , cascade = CascadeType.ALL)
    private Set<Variants> variants ;

    private String name ;
    private String description ;
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "products" , cascade = CascadeType.ALL)
    private Set<ImagePaths> imagePaths ;
    private LocalDateTime createdOn ;
    private LocalDateTime updatedOn ;
    private Boolean status ;
    private Integer totalQuantity ;
}
