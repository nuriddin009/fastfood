package uz.fastfood.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.fastfood.dashboard.dto.*;
import uz.fastfood.dashboard.dto.request.CategoryRequest;
import uz.fastfood.dashboard.entity.Category;
import uz.fastfood.dashboard.entity.Product;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryCreateDTO createDTO);

    void updateEntity(@MappingTarget Category category, CategoryUpdateDTO updateDTO);


    CategoryDTO getCategoryDTO(Category category);
}
