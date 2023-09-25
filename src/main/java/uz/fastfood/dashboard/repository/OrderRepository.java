package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.Order;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.projection.OrderProjection;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {


//    @Query(value = "", nativeQuery = true)
//    Page<OrderProjection> getOrderByRow(OrderStatus status, Pageable pageable);




}
