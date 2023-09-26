package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.Product;
import uz.fastfood.dashboard.filter.ProductFilter;

@Repository
public interface ProductRepositoryCustom {
    Page<Product> findALlByFilter(ProductFilter filter);
}
