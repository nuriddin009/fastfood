package uz.fastfood.dashboard.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductProjectionV2 {

    UUID getId();
    BigDecimal getPrice();
    String getName();

}
