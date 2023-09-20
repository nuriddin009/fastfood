package uz.fastfood.dashboard.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.OrderItemRequest;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.Order;
import uz.fastfood.dashboard.entity.Product;
import uz.fastfood.dashboard.repository.OrderRepository;
import uz.fastfood.dashboard.repository.ProductRepository;
import uz.fastfood.dashboard.service.OrderService;
import uz.fastfood.dashboard.service.UserSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

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


            orderRepository.save(Order.builder()
                    .customer(userSession.getUser())
                    .operator(null)
                    .totalPrice(sum)
                    .build());

            response.setMessage("Order created");


        } catch (Exception e) {
            response.setError(true);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
