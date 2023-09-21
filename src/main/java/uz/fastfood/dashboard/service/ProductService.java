package uz.fastfood.dashboard.service;

import uz.fastfood.dashboard.dto.response.ApiResponse;

import java.util.UUID;

public interface ProductService {
    ApiResponse getProducts(String priceSort, String nameSort, UUID categorySort, Integer page);

}
