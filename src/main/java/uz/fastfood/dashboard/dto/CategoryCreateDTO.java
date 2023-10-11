package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CategoryCreateDTO extends BaseAuditDTO {
    private UUID id;
    private String nameRu;
    private String nameUz;
//    private List<CategoryCreateDTO> children = new ArrayList<>();
}
