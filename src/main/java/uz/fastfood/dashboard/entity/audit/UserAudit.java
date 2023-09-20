package uz.fastfood.dashboard.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.fastfood.dashboard.entity.User;

@Data
@EqualsAndHashCode(exclude = {"createdBy", "updatedBy"})
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class UserAudit {

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(updatable = false)
    private User createdBy;

    @LastModifiedBy
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;

}