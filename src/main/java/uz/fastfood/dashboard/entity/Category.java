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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends BaseEntity {


    @Column(nullable = false)
    private String nameUz;
    @Column(nullable = false)
    private String nameRu;

    @ManyToOne
    private Category parentCategory;

}
