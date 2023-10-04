package uz.fastfood.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.entity.template.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends BaseEntity {

    private Integer orderNumber;
    private BigDecimal orderCost;
    private BigDecimal shippingCost;
    @ManyToOne
    private User customer;
    @ManyToOne
    private User currier;
    @ManyToOne
    private User operator;
    @ManyToOne
    private Branch branch;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")  /**/
    private List<OrderItem> orderItems = new ArrayList<>();

    private Double longitude;
    private Double latitude;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

}
