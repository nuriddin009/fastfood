package uz.fastfood.dashboard.controller;


import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.fastfood.dashboard.dto.*;
import uz.fastfood.dashboard.filter.CategoryFilter;
import uz.fastfood.dashboard.filter.PageFilter;
import uz.fastfood.dashboard.filter.ProductFilter;
import uz.fastfood.dashboard.service.CategoryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryCreateDTO> createCategory(@RequestBody CategoryCreateDTO createDTO) {
        return ResponseEntity.ok(categoryService.create(createDTO));
    }

    @PutMapping
    public ResponseEntity<CategoryUpdateDTO> updateCategory(@RequestBody CategoryUpdateDTO updateDTO) {
        return ResponseEntity.ok(categoryService.update(updateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAllProducts(@ParameterObject CategoryFilter filter) {
        return ResponseEntity.ok(categoryService.getAllProducts(filter));
    }

}
