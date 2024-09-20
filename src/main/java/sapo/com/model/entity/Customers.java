package sapo.com.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "customers" , cascade = CascadeType.ALL)
    private List<Orders> orders ;
    private String code ;
    private String name ;
    private String phoneNumber ;
    private String email;
    private Boolean gender ;
    private String note ;
    private Boolean status ;
    private String address ;
    private LocalDateTime createdOn ;
    private LocalDateTime updatedOn ;
    private BigDecimal totalExpense ;
    private Integer numberOfOrder ;
    private LocalDate birthday ;

}
