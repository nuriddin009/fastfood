package uz.fastfood.dashboard.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.fastfood.dashboard.entity.enums.Status;

@Data
public class ClientRequest {

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private int orderCount;
    @NotNull
    private Status status;

}
