package uz.fastfood.dashboard.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    @NotNull
    private UUID productId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;


}
