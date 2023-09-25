package uz.fastfood.dashboard.projection;

import java.math.BigDecimal;

public interface OrderProjection {

    Integer getOrderNumber();
    BigDecimal getTotalPrice();
    CustomerProjection getCustomer();
    CustomerProjection getOperator();
    BranchProjection getBranch();

}
