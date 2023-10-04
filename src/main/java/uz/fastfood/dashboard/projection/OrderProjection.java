package uz.fastfood.dashboard.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderProjection {

    UUID getId();

    Integer getOrderNumber();

    BigDecimal getOrderCost();

    BigDecimal getShippingCost();

    CustomerProjection getCustomer();

    CustomerProjection getOperator();

    BranchProjection getBranch();

    default List<OrderItemProjection> getOrderItems() {
        return null;
    }




}
