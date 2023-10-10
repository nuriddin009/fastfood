package uz.fastfood.dashboard.repository.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.filter.UserFilter;
import uz.fastfood.dashboard.repository.UserCustomRepository;

@Repository
public class UserRepositoryImpl implements UserCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<User> findAllByFilter(UserFilter filter) {
        final boolean hasSearch = StringUtils.isNotEmpty(filter.getSearch());
        final boolean hasSort = filter.formPageable().getSort().isSorted();

        StringBuilder sql = new StringBuilder("select t from User t where t.deleted=false ");

        if (filter.getFrom() != null) {
            sql.append(" and t.createdAt>=:from");
        }
        if (filter.getTo() != null) {
            sql.append(" and t.createdAt<=:to");
        }

        if (filter.getStatus() != null) {
            sql.append(" and t.status=:status");
        }

        if (hasSearch) {
            sql.append(" and (lower(t.firstname) like :searchKey ");
            sql.append("or lower(t.lastname) like :searchKey");
            sql.append(")");
        }

        String countSql = sql.toString().replaceFirst("select t", "select count(t)");
        sql.append(" order by ");

        if (hasSort) {
            for (Sort.Order order : filter.formPageable().getSort()) {
                sql.append("t.").append(order.getProperty());
                if (order.isDescending()) {
                    sql.append(" desc");
                }
                sql.append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
        } else {
            sql.append(" t.id");
        }

        TypedQuery<User> query = entityManager.createQuery(sql.toString(), User.class)
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
        if (filter.getStatus() != null) {
            query.setParameter("status", filter.getStatus().name());
            countQuery.setParameter("status", filter.getStatus().name());
        }
        if (hasSearch) {
            query.setParameter("searchKey", filter.getSearchForQuery());
            countQuery.setParameter("searchKey", filter.getSearchForQuery());
        }

        return new PageImpl<>(query.getResultList(), filter.getPageable(), countQuery.getSingleResult());
    }
}
