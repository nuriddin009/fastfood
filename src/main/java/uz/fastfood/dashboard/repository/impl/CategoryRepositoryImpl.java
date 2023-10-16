package uz.fastfood.dashboard.repository.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.Category;
import uz.fastfood.dashboard.filter.CategoryFilter;
import uz.fastfood.dashboard.repository.CategoryRepositoryCustom;

@Component
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Category> findAllByFilter(CategoryFilter filter) {

        final boolean hasSearch = StringUtils.isNotEmpty(filter.getSearch());
        StringBuilder sql = new StringBuilder("select t from Category t where t.deleted=false ");

        if (filter.getFrom() != null) {
            sql.append(" and t.createdAt>=:from");
        }
        if (filter.getTo() != null) {
            sql.append(" and t.createdAt<=:to");
        }

        if (hasSearch) {
            sql.append(" and (lower(t.nameUz) like :searchKey ");
            sql.append(" or lower(t.nameRu) like :searchKey ");
            sql.append(")");
        }


        String countSql = sql.toString().replaceFirst("select t", "select count(t)");
        TypedQuery<Category> query = entityManager.createQuery(sql.toString(), Category.class)
                .setFirstResult(filter.getStart()).setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.getFrom() != null) {
            query.setParameter("from", filter.getFrom());
            countQuery.setParameter("from", filter.getFrom());
        }
        if (filter.getTo() != null) {
            query.setParameter("to", filter.getTo());
            countQuery.setParameter("to", filter.getTo());
        }
        if (hasSearch) {
            query.setParameter("searchKey", filter.getSearchForQuery());
            countQuery.setParameter("searchKey", filter.getSearchForQuery());
        }



        return new PageImpl<>(query.getResultList(), filter.getPageable(), countQuery.getSingleResult());
    }
}
