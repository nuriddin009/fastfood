package uz.fastfood.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.Branch;

import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {
}
