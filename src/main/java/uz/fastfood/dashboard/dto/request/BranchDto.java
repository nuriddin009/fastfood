package uz.fastfood.dashboard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BranchDto {

    private UUID branchId;
    @NotBlank
    private String nameUz;
    @NotBlank
    private String nameRu;
    @NotBlank
    private String workingTime;
    @NotBlank
    private String landMark;

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;


}
