package uz.fastfood.dashboard.projection;

import uz.fastfood.dashboard.entity.enums.Status;

import java.util.UUID;

public interface CustomerSearchProjection {


    UUID getId();
    String getFirstname();
    String getLastname();
    String getPhoneNumber();
    Integer getOrderCount();
    Status getStatus();


}
