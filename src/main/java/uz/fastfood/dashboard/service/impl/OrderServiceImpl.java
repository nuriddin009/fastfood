package uz.fastfood.dashboard.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.OrderItemRequest;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.Order;
import uz.fastfood.dashboard.entity.Product;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.projection.OrderProjection;
import uz.fastfood.dashboard.repository.OrderRepository;
import uz.fastfood.dashboard.repository.ProductRepository;
import uz.fastfood.dashboard.service.OrderService;
import uz.fastfood.dashboard.service.UserSession;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final UserSession userSession;
    private final OrderRepository orderRepository;

    @Override
    public BaseResponse<?> makeOrder(OrderRequest request, BaseResponse<?> response) {


        try {
            List<OrderItemRequest> orderItems = request.getOrderItems();

            BigDecimal sum = BigDecimal.valueOf(0);

            for (OrderItemRequest orderItem : orderItems) {
                Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(() -> new NoSuchElementException("Product not found"));
                BigDecimal productCount = BigDecimal.valueOf(orderItem.getProductCount());
                BigDecimal totalPriceForItem = productCount.multiply(product.getPrice());
                sum = sum.add(totalPriceForItem);
            }


            Order order = orderRepository.save(Order.builder()
                    .customer(userSession.getUser())
                    .operator(null)
                    .totalPrice(sum)
                    .orderStatus(OrderStatus.PENDING)
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .build());

            response.setMessage("Order created");
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponse<?> changeStatusOrder(UUID orderId, OrderStatus orderStatus, BaseResponse<?> response) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException(orderId + " not found"));
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            response.setMessage("Order status changed to " + orderStatus);
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getOrders(OrderStatus orderStatus, Integer page, Integer size) {
        Page<OrderProjection> ordersByRow = orderRepository.findAllByOrderStatusOrderByCreatedAtDesc(orderStatus, PageRequest.of(page, size));
        return new ApiResponse(true, ordersByRow, "Orders by rows");
    }

    @Override
    public ApiResponse getOrdersV2(Integer page, Integer size) {
        Map<OrderStatus, Page<OrderProjection>> listMap = new HashMap<>();

        Pageable pageable = PageRequest.of(page - 1, size);
        for (OrderStatus value : OrderStatus.values()) {
            listMap.put(value, orderRepository.findAllByOrderStatusOrderByCreatedAtDesc(value, pageable));
        }

        return new ApiResponse(true, listMap, "Orders by columns");
    }
}
