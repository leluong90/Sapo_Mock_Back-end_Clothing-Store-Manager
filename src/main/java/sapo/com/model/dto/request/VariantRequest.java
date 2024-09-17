package sapo.com.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import sapo.com.model.entity.ImagePath;
import sapo.com.model.entity.Variant;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class VariantRequest {
    private Long id;
    private Long productId;
    @Valid
    @NotBlank
    private String name;
    private String sku;
    private String size;
    private String color;
    private String material;
    @Valid
    @NotBlank
    @Min(0)
    private BigDecimal initialPrice;
    @Valid
    @NotBlank
    @Min(0)
    private BigDecimal priceForSale;
    private String imagePath;
    private Boolean status;
}
