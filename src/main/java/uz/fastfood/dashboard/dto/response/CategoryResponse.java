package uz.fastfood.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryResponse {

    private UUID id;
    private String name;


}
