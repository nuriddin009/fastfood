package uz.fastfood.dashboard.service;

import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.enums.OrderStatus;

import java.util.UUID;

public interface OrderService {
    BaseResponse<?> makeOrder(OrderRequest request, BaseResponse<?> response);
    BaseResponse<?> changeStatusOrder(UUID orderId, OrderStatus orderStatus, BaseResponse<?> response);

    ApiResponse getOrders(OrderStatus orderStatus);

}
