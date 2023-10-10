package uz.fastfood.dashboard.dto;


import lombok.Data;
import uz.fastfood.dashboard.entity.enums.Status;

import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private int orderVolume;
    private Status status;


}
