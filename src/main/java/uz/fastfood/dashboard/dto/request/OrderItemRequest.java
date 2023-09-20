package uz.fastfood.dashboard.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequest {


    private UUID productId;

    private int productCount;


}
