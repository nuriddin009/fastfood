package uz.fastfood.dashboard.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.Branch;
import uz.fastfood.dashboard.projection.MaxOrderNumber;
import uz.fastfood.dashboard.repository.OrderRepository;

@Component
@RequiredArgsConstructor
public class OrderNumberCreator {


    private final OrderRepository orderRepository;

    public int createOrderNumber(Branch branch) {
       MaxOrderNumber maxOrderNumber = orderRepository.getMaxOrderNumber(branch.getId());
        if (maxOrderNumber.getOrderNumber() <= 500 || maxOrderNumber.getOrderSize() == 0) {
            return maxOrderNumber.getOrderNumber() + 1;
        }
        return 1;
    }
}



