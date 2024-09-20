package sapo.com.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "variants")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    //    @ManyToOne
//    @JoinColumn(name = "creator_id")
//    private Long creatorId;
    private String name;
    private String sku;
    private String size;
    private String color;
    private String material;
    private Long quantity;
    @Column(name = "initial_price")
    private BigDecimal initialPrice;
    @Column(name = "price_for_sale")
    private BigDecimal priceForSale;
    private Boolean status ;
    @Column(name = "image_path")
    private String imagePath ;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_on")
    private LocalDateTime createdOn ;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_on")
    private LocalDateTime updatedOn ;
}