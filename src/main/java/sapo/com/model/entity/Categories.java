package sapo.com.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "categories" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Products> products ;
    private String code ;
    private String name ;
    private String description ;
    private LocalDateTime createdOn ;
    private LocalDateTime updatedOn ;
}
