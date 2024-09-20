package sapo.com.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImagePaths {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String path ;
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "product_id" )
    private Products products ;
}
