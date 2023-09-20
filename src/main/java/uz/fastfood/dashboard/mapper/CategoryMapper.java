package uz.fastfood.dashboard.mapper;

import org.mapstruct.Mapper;
import uz.fastfood.dashboard.dto.request.CategoryRequest;
import uz.fastfood.dashboard.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<CategoryRequest, Category> {

    @Override
    CategoryRequest toDto(Category category);

    @Override
    Category toEntity(CategoryRequest request);
}
