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
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "brands" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Products> products ;
    private String code ;
    private String name ;
    private String description ;
    private LocalDateTime createdOn ;
    private LocalDateTime updateOn ;
}
