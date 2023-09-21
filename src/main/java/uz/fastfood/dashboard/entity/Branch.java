package uz.fastfood.dashboard.entity;


import jakarta.persistence.Entity;
import lombok.*;
import uz.fastfood.dashboard.entity.template.BaseEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Branch extends BaseEntity {

    private String nameUz;
    private String nameRu;
    private String landmark;
    private String workingTime;

    private Double longitude;
    private Double latitude;

    private boolean deleted = false;

}
