package uz.fastfood.dashboard.service;

import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.projection.ProductProjection;

import java.util.UUID;

public interface ProductService {
    BaseResponse<ProductProjection> getProducts(String priceSort, String nameSort, UUID categorySort,Integer page);
}
