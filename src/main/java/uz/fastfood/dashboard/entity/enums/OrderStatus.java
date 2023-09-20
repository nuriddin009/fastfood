package uz.fastfood.dashboard.entity.enums;

public enum OrderStatus {

    PENDING,      // Order has been placed but not yet processed
    PROCESSING,   // Order is being prepared or processed
    SHIPPED,      // Order has been shipped
    DELIVERED,    // Order has been delivered to the customer
    CANCELED      // Order has been canceled

}
