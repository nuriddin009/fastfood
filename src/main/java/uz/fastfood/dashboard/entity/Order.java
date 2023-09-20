package uz.fastfood.dashboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.fastfood.dashboard.entity.template.BaseEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends BaseEntity {



    @ManyToOne
    private User customer;


    @ManyToOne
    private User operator;


}
