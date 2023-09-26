package uz.fastfood.dashboard.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrderProjection {

    UUID getId();
    Integer getOrderNumber();
    BigDecimal getTotalPrice();
    CustomerProjection getCustomer();
    CustomerProjection getOperator();
    BranchProjection getBranch();

}
