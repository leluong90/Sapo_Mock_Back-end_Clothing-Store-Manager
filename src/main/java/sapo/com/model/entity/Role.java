package sapo.com.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String name  ;
}
//    @OneToMany(mappedBy = "roles"  , fetch = FetchType.EAGER)
//    @JsonIgnore
//    private Set<User> users ;
