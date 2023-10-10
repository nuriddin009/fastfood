package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.filter.UserFilter;

public interface UserCustomRepository {
    Page<User> findAllByFilter(UserFilter filter);
}
