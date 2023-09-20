package uz.fastfood.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDTO {
    private String nameRu;
    private String nameUz;
    private List<CategoryCreateDTO> children = new ArrayList<>();
}
