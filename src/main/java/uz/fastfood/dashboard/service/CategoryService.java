package uz.fastfood.dashboard.service;

import uz.fastfood.dashboard.dto.request.CategoryRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;

import java.util.UUID;

public interface CategoryService {

    BaseResponse<?> createCategory(CategoryRequest request, BaseResponse<?> response);
    ApiResponse update(CategoryRequest request);

    ApiResponse delete(UUID id);
}
