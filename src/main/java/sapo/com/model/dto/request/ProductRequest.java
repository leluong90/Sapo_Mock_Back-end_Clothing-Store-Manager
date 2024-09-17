package sapo.com.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import sapo.com.model.entity.Brand;
import sapo.com.model.entity.Category;
import sapo.com.model.entity.ImagePath;
import sapo.com.model.entity.Variant;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ProductRequest {
    @Valid
    @NotBlank
    private String name;
    private Long categoryId;
    private Long brandId;
    private String description;
    private Set<String> imagePath;
    @Valid
    @NotBlank
    private Set<VariantRequest> variants;

}
