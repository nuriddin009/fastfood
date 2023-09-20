package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO extends BaseAuditDTO{
    private String name;
    private String description;
    private BigDecimal price;
    private AttachmentDTO attachment;
    private CategoryDTO category;
}
