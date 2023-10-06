package uz.fastfood.dashboard.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.OrderItemRequest;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.*;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.filter.OrderFilter;
import uz.fastfood.dashboard.projection.OrderDetails;
import uz.fastfood.dashboard.projection.OrderProjection;
import uz.fastfood.dashboard.repository.BranchRepository;
import uz.fastfood.dashboard.repository.OrderItemRepository;
import uz.fastfood.dashboard.repository.OrderRepository;
import uz.fastfood.dashboard.repository.ProductRepository;
import uz.fastfood.dashboard.service.DistanceService;
import uz.fastfood.dashboard.service.OrderService;
import uz.fastfood.dashboard.service.UserSession;
import uz.fastfood.dashboard.utils.OrderNumberCreator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final UserSession userSession;
    private final OrderRepository orderRepository;
    private final DistanceService distanceService;
    private final BranchRepository branchRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderNumberCreator orderNumberCreator;

    @Override
    public BaseResponse<?> makeOrder(OrderRequest request, BaseResponse<?> response) {


        try {
            List<OrderItemRequest> orderItems = request.getOrderItems();

            BigDecimal sum = BigDecimal.valueOf(0);

            List<OrderItem> orderItemList = new ArrayList<>();

            for (OrderItemRequest orderItem : orderItems) {
                Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(() -> new NoSuchElementException("Product not found"));

                orderItemList.add(orderItemRepository.save(OrderItem.builder()
                        .product(product)
                        .quantity(orderItem.getQuantity())
                        .build()));


                BigDecimal productCount = BigDecimal.valueOf(orderItem.getQuantity());
                BigDecimal totalPriceForItem = productCount.multiply(product.getPrice());
                sum = sum.add(totalPriceForItem);
            }

            Branch nearestBranch = findNearestBranch(request.getLatitude(), request.getLongitude());

            double distance = 5; /*distanceService.calculateDistance(
                    request.getLatitude(), request.getLongitude(),
                    nearestBranch.getLatitude(), nearestBranch.getLongitude()
            ); */


            Order order = orderRepository.save(Order.builder()
                    .customer(userSession.getUser())
                    .operator(null)
                    .orderCost(sum)
                    .orderNumber(orderNumberCreator.createOrderNumber(nearestBranch))
                    .shippingCost(calculateShippingCost(distance))
                    .orderStatus(OrderStatus.PENDING)
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .branch(nearestBranch)
                    .orderItems(orderItemList)
                    .build());

            orderItemList.forEach(orderItem -> orderItem.setOrder(order));
            List<OrderItem> orderItemList1 = orderItemRepository.saveAll(orderItemList);
            System.out.println(orderItemList1);


            response.setMessage("Order created");
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public BaseResponse<?> makeOrderByAdmin(OrderRequest request, BaseResponse<?> response) {


        return null;
    }

    @Transactional
    @Override
    public BaseResponse<?> changeStatusOrder(UUID orderId, OrderStatus orderStatus, BaseResponse<?> response) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException(orderId + " not found"));
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            response.setMessage("Order status changed to " + orderStatus.name());
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public ApiResponse getOrders(OrderStatus orderStatus, Integer page, Integer size) {
        Page<OrderProjection> ordersByRow = orderRepository.getOrdersByOrderStatus(orderStatus.name(), PageRequest.of(page - 1, size));
        return new ApiResponse(true, ordersByRow, "Orders by rows");
    }

    @Override
    public ApiResponse getOrdersV2(Integer page, Integer size) {
        Map<String, List<OrderProjection>> listMap = new HashMap<>();

        Pageable pageable = PageRequest.of(page - 1, size);
        for (OrderStatus value : OrderStatus.values()) {
            listMap.put(value.name().toLowerCase(), orderRepository.getOrdersByOrderStatus(value.name(), pageable).getContent());
        }

        return new ApiResponse(true, listMap, "Orders by columns");
    }

    @Override
    public ApiResponse attachOperator(UUID orderId) {
        User operator = userSession.getUser();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found with id=" + orderId));
        order.setOperator(operator);
        orderRepository.save(order);
        return new ApiResponse(true, "Operator attached");
    }

    @Override
    public ApiResponse deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found with id=" + orderId));
        order.setDeleted(true);
        orderRepository.save(order);
        return new ApiResponse(true, "order deleted");
    }

    @Override
    public ApiResponse orderDetail(UUID orderId) {
        OrderDetails order = orderRepository.findByIdAndDeletedFalse(orderId);
        return new ApiResponse(true, order);
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert latitude and longitude from degrees to radians
        double radiansLat1 = Math.toRadians(lat1);
        double radiansLon1 = Math.toRadians(lon1);
        double radiansLat2 = Math.toRadians(lat2);
        double radiansLon2 = Math.toRadians(lon2);

        // Haversine formula
        double dLat = radiansLat2 - radiansLat1;
        double dLon = radiansLon2 - radiansLon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(radiansLat1) * Math.cos(radiansLat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;
    }

    private Branch findNearestBranch(double myLat, double myLon) {
        List<Branch> branches = branchRepository.findAllByDeletedFalse();
        Branch nearestBranch = null;
        double minDistance = Double.MAX_VALUE;

        for (Branch branch : branches) {
            double distance = calculateDistance(myLat, myLon, branch.getLatitude(), branch.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestBranch = branch;
            }
        }

        return nearestBranch;
    }


    private BigDecimal calculateShippingCost(double distance) {

        double baseFee = 5000;
        double ratePerKilometer = 1000;

        double[] distanceTiers = {10, 20, 30, 40};  // distance tiers in kilometers
        double[] rateTiers = {1000, 1200, 1500, 2000}; // rates for each tier


        for (int i = 0; i < distanceTiers.length; i++) {
            if (distance <= distanceTiers[i]) {
                ratePerKilometer = rateTiers[i];
                break;
            }
        }

        return BigDecimal.valueOf(baseFee + (distance * ratePerKilometer));
    }

}
