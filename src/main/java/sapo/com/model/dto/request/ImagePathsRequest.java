package sapo.com.model.dto.request;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import sapo.com.model.entity.Products;

@Getter
@Setter
public class ImagePathsRequest {
    private String path ;
    private Products products ;
}
