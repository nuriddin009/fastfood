package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.Branch;
import uz.fastfood.dashboard.projection.BranchProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    @Query(value = "select *\n" +
            "from branch b\n" +
            "where (upper(b.name_uz) ilike upper('%' || :search || '%') or upper(b.name_ru) ilike upper('%' || :search || '%')) and b.deleted is false",
            countQuery = "select *\n" +
                    "from branch b\n" +
                    "where (upper(b.name_uz) ilike upper('%' || :search || '%') or upper(b.name_ru) ilike upper('%' || :search || '%')) and b.deleted is false", nativeQuery = true)
    Page<BranchProjection> getBranches(String search, Pageable pageable);


    List<Branch> findAllByDeletedFalse();


}
