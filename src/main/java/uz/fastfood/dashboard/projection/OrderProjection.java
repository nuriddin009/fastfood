package uz.fastfood.dashboard.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrderProjection {

    UUID getId();

    Integer getOrderNumber();

    BigDecimal getOrderCost();

    BigDecimal getShippingCost();

    @Value("#{@orderRepository.findOrderCustomer(target.id)}")
    CustomerProjection getCustomer();

    @Value("#{@orderRepository.findOrderOperator(target.id)}")
    CustomerProjection getOperator();

    @Value("#{@orderRepository.findOrderBranch(target.id)}")
    BranchProjection getBranch();

//    default List<OrderItemProjection> getOrderItems() {
//        return null;
//    }




}
