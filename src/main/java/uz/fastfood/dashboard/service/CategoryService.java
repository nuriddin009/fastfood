package uz.fastfood.dashboard.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.CategoryCreateDTO;
import uz.fastfood.dashboard.dto.CategoryDTO;
import uz.fastfood.dashboard.dto.CategoryUpdateDTO;
import uz.fastfood.dashboard.entity.Category;
import uz.fastfood.dashboard.entity.Product;
import uz.fastfood.dashboard.filter.CategoryFilter;
import uz.fastfood.dashboard.filter.PageFilter;
import uz.fastfood.dashboard.mapper.CategoryMapper;
import uz.fastfood.dashboard.repository.CategoryRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryCreateDTO create(CategoryCreateDTO createDTO) {
        Category category = categoryMapper.toEntity(createDTO);
        categoryRepository.save(category);
        return createDTO;
    }

    public CategoryUpdateDTO update(CategoryUpdateDTO updateDTO) {
        Category category = categoryRepository.findById(updateDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        categoryMapper.updateEntity(category, updateDTO);
        categoryRepository.save(category);
        return updateDTO;
    }


    public CategoryDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return categoryMapper.getCategoryDTO(category);
    }

    public Page<CategoryDTO> getAllProducts(CategoryFilter filter) {
        return categoryRepository.findAllByFilter(filter).map(categoryMapper::getCategoryDTO);
    }
}
