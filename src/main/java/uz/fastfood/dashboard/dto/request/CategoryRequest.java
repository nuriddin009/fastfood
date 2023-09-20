package uz.fastfood.dashboard.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequest {

    @NotBlank
    private String nameUz;
    @NotBlank
    private String nameRu;
    @NotNull
    private UUID categoryId;

}
