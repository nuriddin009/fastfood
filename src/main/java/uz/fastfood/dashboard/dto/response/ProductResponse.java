package uz.fastfood.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.fastfood.dashboard.entity.Category;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String fileUrl;
    private CategoryResponse category;
    private String description;

}
