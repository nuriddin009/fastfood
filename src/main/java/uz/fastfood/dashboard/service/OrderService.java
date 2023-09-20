package uz.fastfood.dashboard.service;

import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.BaseResponse;

public interface OrderService {
    BaseResponse<?> makeOrder(OrderRequest request, BaseResponse<?> response);
}
