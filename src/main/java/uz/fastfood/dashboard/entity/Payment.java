package uz.fastfood.dashboard.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.fastfood.dashboard.entity.template.BaseEntity;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment extends BaseEntity {


    private BigDecimal amount;

    @OneToOne
    private Order order;


}
