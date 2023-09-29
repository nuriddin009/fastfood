package uz.fastfood.dashboard.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequest {

    @NotNull
    private UUID productId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;


}
