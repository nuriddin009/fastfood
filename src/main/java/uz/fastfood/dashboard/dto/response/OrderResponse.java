package uz.fastfood.dashboard.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OrderResponse {

    private UUID id;
    private int orderNumber;
    private BigDecimal orderCost;
    private BigDecimal shippingCost;
}
