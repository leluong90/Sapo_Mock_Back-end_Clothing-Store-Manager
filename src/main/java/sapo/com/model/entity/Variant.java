package sapo.com.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import sapo.com.model.dto.request.VariantRequest;

import java.math.BigDecimal;
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
@Table(name = "variants")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name = "product_id")
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
    private LocalDateTime updateOn ;

    public void transferFromRequest(VariantRequest variantRequest){
        this.name=variantRequest.getName();
        this.sku=variantRequest.getSku();
        this.size=variantRequest.getSize();
        this.color= variantRequest.getColor();
        this.material=variantRequest.getMaterial();
        this.initialPrice=variantRequest.getInitialPrice();
        this.priceForSale=variantRequest.getPriceForSale();
        this.imagePath=variantRequest.getImagePath();
        this.status=variantRequest.getStatus();
    }


}