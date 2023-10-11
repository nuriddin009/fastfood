package uz.fastfood.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.dto.response.CategoryResponse;
import uz.fastfood.dashboard.entity.Category;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, CategoryRepositoryCustom {

    @Query(value = "select t.id as id, t.nameUz as nameUz, t.nameRu  as nameRu , pt as parent from Category t left join Category pt on t.parentId=pt.id\n" +
            " where (t.nameUz like :searchKey or t.nameRu like :searchKey)\n" +
            "  and t.deleted = false", countQuery = "select t.id as id, t.nameUz as nameUz, t.nameRu  as nameRu , pt as parent from Category t left join Category pt on t.parentId=pt.id\n" +
            " where (t.nameUz like :searchKey or t.nameRu like :searchKey)\n" +
            "  and t.deleted = false")
    Page<CategoryResponse> findAllBy(String searchKey, Pageable pageable);

}
