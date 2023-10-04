package uz.fastfood.dashboard.projection;


import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderDetails {

    UUID getId();

    Integer getOrderNumber();

    BigDecimal getOrderCost();

    BigDecimal getShippingCost();

    CustomerProjection getCustomer();

    CustomerProjection getOperator();

    BranchProjection getBranch();

    List<OrderItemProjection> getOrderItems();

}
