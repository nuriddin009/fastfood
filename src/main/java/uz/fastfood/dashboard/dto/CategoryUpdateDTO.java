package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryUpdateDTO extends BaseAuditDTO {
    private String nameRu;
    private String nameUz;
    private List<CategoryCreateDTO> children = new ArrayList<>();
}
