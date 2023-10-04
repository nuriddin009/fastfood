package uz.fastfood.dashboard.repository;

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


//    @Query(value = "select t.orderItems as orderItems from Order t where t.id=:orderId")
//    List<OrderItemProjection> findAllByOrderId(UUID orderId);


}
