package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import uz.fastfood.dashboard.entity.Category;
import uz.fastfood.dashboard.filter.PageFilter;

public interface CategoryRepositoryCustom {
    Page<Category> findAllByFilter (PageFilter filter);
}
