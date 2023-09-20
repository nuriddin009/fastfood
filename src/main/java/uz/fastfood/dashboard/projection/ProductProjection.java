package uz.fastfood.dashboard.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductProjection {

    UUID getId();
    BigDecimal getPrice();
    CategoryDemo getCategory();
    String getDescription();

}
