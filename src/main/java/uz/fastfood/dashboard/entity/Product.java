package uz.fastfood.dashboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.fastfood.dashboard.entity.template.BaseEntity;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {




    @Column(nullable = false)
    private String name;


    private String description;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    @ManyToOne
    private Category category;

}
