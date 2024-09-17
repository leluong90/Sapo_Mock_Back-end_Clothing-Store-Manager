package sapo.com.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Variants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Products products ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User user ;

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "variants" , cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetails ;

    private String sku ;
    private String name ;
    private String size ;
    private String color ;
    private String material ;
    private BigDecimal initialPrice ;
    private BigDecimal priceForSale ;
    private Integer quantity ;
    private String imagePath ;
    private Boolean status ;
    private LocalDateTime createdOn ;
    private LocalDateTime updatedOn ;

}
