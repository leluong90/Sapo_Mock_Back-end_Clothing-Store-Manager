package sapo.com.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Min(0)
    private BigDecimal initialPrice;
    @Valid
    @NotNull
    @Min(0)
    private BigDecimal priceForSale;
    private String imagePath;

    // Do not have product
    public Variant transferToVariant(){
        Variant variant =new Variant();
        variant.setName(this.name);
        variant.setId(this.id);
        variant.setSku(this.sku);
        variant.setSize(this.size);
        variant.setColor(this.color);
        variant.setMaterial(this.material);
        variant.setInitialPrice(this.initialPrice);
        variant.setPriceForSale(this.priceForSale);
        variant.setImagePath(this.imagePath);
        return variant;
    }


}
