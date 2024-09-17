package sapo.com.model.entity;

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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customers customers ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User user ;
    @OneToOne(fetch = FetchType.LAZY , mappedBy = "orders" , cascade = CascadeType.ALL)
    private OrderDetails orderDetails ;


    private String code ;
    private LocalDateTime createdOn ;
    private Integer totalQuantity ;
    private String note ;
    private Integer cashReceive ;
    private Integer cashRepay ;
    private BigDecimal totalPayment ;
    private ECash paymentType ;
}
