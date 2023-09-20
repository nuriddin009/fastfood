package uz.fastfood.dashboard.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.projection.ProductProjection;
import uz.fastfood.dashboard.service.ProductService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final EntityManager entityManager;

    @Override
    public BaseResponse<ProductProjection> getProducts(String priceSort, String nameSort, UUID categorySort, Integer page) {

        List<Sort.Order> sorts = new ArrayList<>();

        if (!priceSort.isEmpty()) {
            sorts.add(new Sort.Order(priceSort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (!nameSort.isEmpty()) {
            sorts.add(new Sort.Order(nameSort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        Sort sort = Sort.by(sorts);

        StringBuilder orderQuery = new StringBuilder(" order by ");
        Sort sort__ = Sort.by(sorts);
        Iterator<Sort.Order> iterator__ = sort__.iterator();
        while (iterator__.hasNext()) {
            Sort.Order order = iterator__.next();
            orderQuery.append(order.getProperty()).append(" ").append(order.getDirection().name()).append(",");
        }
        orderQuery.deleteCharAt(orderQuery.length() - 1);

        String query = "select * from Product t" + orderQuery;

        return null;
    }
}
