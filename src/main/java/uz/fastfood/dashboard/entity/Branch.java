package uz.fastfood.dashboard.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import uz.fastfood.dashboard.entity.template.BaseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<User> operators = new HashSet<>();

}
