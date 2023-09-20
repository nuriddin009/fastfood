package uz.fastfood.dashboard.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    @Override
    public BaseResponse<?> makeOrder(OrderRequest request, BaseResponse<?> response) {
        return null;
    }
}
