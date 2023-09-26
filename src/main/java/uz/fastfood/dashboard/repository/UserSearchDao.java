package uz.fastfood.dashboard.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.fastfood.dashboard.entity.User;
import uz.fastfood.dashboard.entity.enums.Status;

import java.util.List;

@Repository
public class UserSearchDao {

    private EntityManager em;


    public List<User> findAllByCriteria(
            String search, String nameSort, String orderSort, Boolean activeSort
    ) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        final Status status = activeSort ? Status.ACTIVE : Status.BLOCK;

        Predicate firstnamePredicate = criteriaBuilder.like(root.get("firstname"), "%" + search + "%");
        Predicate lastnamePredicate = criteriaBuilder.like(root.get("lastname"), "%" + search + "%");
        Predicate activePredicate = criteriaBuilder.equal(root.get("status"), status);

        Predicate orPredicate = criteriaBuilder.or(firstnamePredicate, lastnamePredicate);

        if (activeSort != null) {
            criteriaQuery.where(
                    criteriaBuilder.and(orPredicate, activePredicate)
            );
        } else {
            criteriaQuery.where(orPredicate);
        }

        TypedQuery<User> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }


}
