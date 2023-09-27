package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.Category;
import uz.fastfood.dashboard.filter.PageFilter;

@Component
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    @Override
    public Page<Category> findAllByFilter(PageFilter filter) {
        return null;
    }
}
