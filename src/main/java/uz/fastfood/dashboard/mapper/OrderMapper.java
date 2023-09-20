package uz.fastfood.dashboard.mapper;

import org.mapstruct.Mapper;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderRequest, Order>{

}
