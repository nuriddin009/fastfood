package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CategoryDTO extends BaseAuditDTO {
    private UUID id;
    private String nameRu;
    private String nameUz;
    private CategoryCreateDTO parent;
//    private List<CategoryCreateDTO> children = new ArrayList<>();

}
