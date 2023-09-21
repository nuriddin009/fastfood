package uz.fastfood.dashboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.fastfood.dashboard.entity.template.BaseEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {


    @Column(nullable = false)
    private String nameUz;
    @Column(nullable = false)
    private String nameRu;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;

    private boolean deleted = false;

}
