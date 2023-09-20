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
@Entity
public class OrderItem extends BaseEntity {


    @ManyToOne
    private Product product;

    private int productCount;


}
