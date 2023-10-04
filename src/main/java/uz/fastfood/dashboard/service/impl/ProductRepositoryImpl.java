package uz.fastfood.dashboard.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.Product;
import uz.fastfood.dashboard.filter.ProductFilter;
import uz.fastfood.dashboard.repository.ProductRepositoryCustom;

@Component
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Page<Product> findALlByFilter(ProductFilter filter) {
        final boolean hasSearch = StringUtils.isNotEmpty(filter.getSearch());
        final boolean hasSort = filter.formPageable().getSort().isSorted();

        StringBuilder sql = new StringBuilder();
        sql.append("select p from Product p where 1=1 ");

        sql.append(" and t.deleted=false");

        if (filter.getFrom() != null) {
            sql.append(" and p.createdAt>=:from");
        }
        if (filter.getTo() != null) {
            sql.append(" and p.createdAt<=:to");
        }
        if (filter.getCategoryId() != null) {
            sql.append(" and p.categoryId=:categoryId");
        }
        if (hasSearch) {
            sql.append(" and (lower(p.name) like :searchKey ");
            sql.append(" or lower(p.description) like :searchKey ");
            sql.append(")");
        }

        String countSql = sql.toString().replaceFirst("select p", "select count(p)");
        sql.append(" order by ");
        if (hasSort) {
            for (Sort.Order order : filter.formPageable().getSort()) {
                sql.append("p.").append(order.getProperty());
                if (order.isDescending()) {
                    sql.append(" desc");
                }
                sql.append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
        } else {
            sql.append(" p.id");
        }

        TypedQuery<Product> query = entityManager.createQuery(sql.toString(), Product.class)
                .setFirstResult(filter.getStart()).setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);


        if (filter.getCategoryId() != null) {
            query.setParameter("categoryId", filter.getCategoryId());
            countQuery.setParameter("categoryId", filter.getCategoryId());
        }
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
