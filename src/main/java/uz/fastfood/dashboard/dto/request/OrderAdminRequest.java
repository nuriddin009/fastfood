package uz.fastfood.dashboard.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderAdminRequest {

    @NotEmpty
    private List<OrderItemRequest> orderItems;

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

    @NotNull
    private UUID customerId;

}
