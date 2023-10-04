package uz.fastfood.dashboard.projection;

public interface OrderItemProjection {

    ProductProjectionV2 getProduct();

    Integer getQuantity();

}
