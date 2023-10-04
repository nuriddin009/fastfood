package uz.fastfood.dashboard.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.OrderItem;
import uz.fastfood.dashboard.projection.OrderItemProjection;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {


//    @Query("SELECT oi FROM Order o JOIN o.orderItems oi WHERE o.id = :orderId")
//    Set<OrderItem> findOrderItemsByOrderId(@Param("orderId") UUID orderId);


}
