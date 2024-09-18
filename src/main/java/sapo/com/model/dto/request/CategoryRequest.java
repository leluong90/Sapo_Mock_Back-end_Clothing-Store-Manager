package sapo.com.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {
    @NotNull
    private String name ;
    private String code ;
    private String description  ;
}
