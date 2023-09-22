package uz.fastfood.dashboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.entity.template.BaseEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends BaseEntity {

    private BigDecimal orderNumber;

    private BigDecimal totalPrice;
    @ManyToOne
    private User customer;
    @ManyToOne
    private User courier;
    @ManyToOne
    private User operator;
    @ManyToOne
    private Branch branch;
    private Double longitude;
    private Double latitude;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;
}
