package uz.fastfood.dashboard.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotEmpty
    private List<OrderItemRequest> orderItems;

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

}
