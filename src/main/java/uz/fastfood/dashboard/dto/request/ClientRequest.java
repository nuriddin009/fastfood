package uz.fastfood.dashboard.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import uz.fastfood.dashboard.entity.enums.Status;

@Data
public class ClientRequest {

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Pattern(regexp = "^(\\+998|8)(\\d{2})(\\d{7})$", message = "Invalid phone number")
    private String phoneNumber;
    @NotNull
    private int orderVolume;
    @NotNull
    private Status status;

}
