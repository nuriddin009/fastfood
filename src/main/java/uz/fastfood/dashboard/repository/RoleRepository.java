package uz.fastfood.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.fastfood.dashboard.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
