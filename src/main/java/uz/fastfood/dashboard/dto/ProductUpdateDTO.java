package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductUpdateDTO extends BaseAuditDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private UUID attachmentId;
    private UUID categoryId;
}