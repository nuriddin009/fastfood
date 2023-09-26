package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import uz.fastfood.dashboard.entity.Product;
import uz.fastfood.dashboard.filter.ProductFilter;

public interface ProductRepositoryCustom {
    Page<Product> findALlByFilter(ProductFilter filter);
}
