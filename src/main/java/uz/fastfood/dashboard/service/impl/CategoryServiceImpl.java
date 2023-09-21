package uz.fastfood.dashboard.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.CategoryRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.repository.CategoryRepository;
import uz.fastfood.dashboard.service.CategoryService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public BaseResponse<?> createCategory(CategoryRequest request, BaseResponse<?> response) {


        try {




        }catch (Exception e){
            response.setError(true);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public ApiResponse update(CategoryRequest request) {



        return null;
    }

    @Override
    public ApiResponse delete(UUID id) {
        return null;
    }
}
