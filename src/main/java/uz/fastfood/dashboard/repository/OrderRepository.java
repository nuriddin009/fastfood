package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.Order;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.projection.*;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {


    @Query(value = "select o.id as id,\n" +
            "       o.order_number        as orderNumber,\n" +
            "       o.order_cost          as orderCost,\n" +
            "       o.shipping_cost       as shippingCost\n" +
            "from orders o\n" +
            "where o.order_status = :orderStatus\n" +
            "  and o.deleted = false", nativeQuery = true)
    Page<OrderProjection> getOrdersByOrderStatus(String orderStatus, Pageable pageable);


    OrderDetails findByIdAndDeletedFalse(UUID id);

    @Query(value = "select b.name_uz   as nameUz,\n" +
            "       b.name_ru   as nameRu,\n" +
            "       b.longitude as longitude,\n" +
            "       b.latitude  as latitude\n" +
            "from orders o\n" +
            "         inner join branch b on b.id = o.branch_id\n" +
            "where o.id = :orderId", nativeQuery = true)
    BranchProjection findOrderBranch(UUID orderId);


    @Query(value = "select concat(u.firstname || ' ' || u.lastname) as name,\n" +
            "       u.phone_number                           as phoneNumber\n" +
            "from orders o\n" +
            "         inner join users u on u.id = o.customer_id\n" +
            "where o.id = :orderId", nativeQuery = true)
    CustomerProjection findOrderCustomer(UUID orderId);

    @Query(value = "select concat(u.firstname || ' ' || u.lastname) as name,\n" +
            "       u.phone_number                           as phoneNumber\n" +
            "from orders o\n" +
            "         inner join users u on u.id = o.operator_id\n" +
            "where o.id = :orderId", nativeQuery = true)
    CustomerProjection findOrderOperator(UUID orderId);

    @Query(value = "select coalesce(max(o.order_number), 0) as orderNumber,\n" +
            "       count(o.id)                      as orderSize\n" +
            "from orders o\n" +
            "where o.branch_id = :branchId\n" +
            "  and o.order_status <> 'DELIVERED'", nativeQuery = true)
    MaxOrderNumber getMaxOrderNumber(UUID branchId);


}
