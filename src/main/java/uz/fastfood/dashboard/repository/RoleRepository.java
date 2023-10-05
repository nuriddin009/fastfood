package uz.fastfood.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.fastfood.dashboard.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {


    Optional<Role> findByName(String name);

}
