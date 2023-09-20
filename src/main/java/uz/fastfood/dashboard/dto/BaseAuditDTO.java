package uz.fastfood.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Getter
@Setter
public class BaseAuditDTO implements Serializable {
    private UUID id;
    @JsonProperty(access = READ_ONLY, index = -101)
    private LocalDateTime createdAt;
    @JsonProperty(access = READ_ONLY, index = -102)
    private LocalDateTime updatedAt;
}
