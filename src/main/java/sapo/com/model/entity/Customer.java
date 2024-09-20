package sapo.com.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    private String email;
    private boolean gender;
    private String note;
    private boolean status;
    private String address;
    private Date birthday;
    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @Column(name = "total_expense")
    private BigDecimal totalExpense;
    @Column(name = "number_of_order")
    private int numberOfOrder;
}
