package sapo.com.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Orders orders ;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "variant_id")
    private Variants variants ;

    private Integer quantity ;
    private BigDecimal subTotal ;
}
