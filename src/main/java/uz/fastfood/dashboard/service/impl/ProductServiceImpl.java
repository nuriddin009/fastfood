package uz.fastfood.dashboard.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.CategoryResponse;
import uz.fastfood.dashboard.dto.response.ProductResponse;
import uz.fastfood.dashboard.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final EntityManager entityManager;

    @Override
    public ApiResponse getProducts(String priceSort, String nameSort, UUID categorySort, Integer page, String search) {

        List<Sort.Order> sorts = new ArrayList<>();

        if (!priceSort.isEmpty()) {
            sorts.add(new Sort.Order(priceSort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "price"));
        }

        if (!nameSort.isEmpty()) {
            sorts.add(new Sort.Order(nameSort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name"));
        }

        Sort sort = Sort.by(sorts);
        Iterator<Sort.Order> iterator = sort.iterator();

        if (!iterator.hasNext()) {
            sorts.add(new Sort.Order(Sort.Direction.DESC, "createdAt"));
        }


        StringBuilder orderQuery = new StringBuilder(" order by ");
        Sort sort__ = Sort.by(sorts);
        Iterator<Sort.Order> iterator__ = sort__.iterator();
        while (iterator__.hasNext()) {
            Sort.Order order = iterator__.next();
            orderQuery.append(order.getProperty()).append(" ").append(order.getDirection().name()).append(",");
        }
        orderQuery.deleteCharAt(orderQuery.length() - 1);


        StringBuilder conQuery = new StringBuilder(" where ");
        if (categorySort != null) conQuery.append("t.category.id = :categorySort");
        conQuery.append(" upper(t.name) like upper('%").append(search).append("%') ");

        String query = "select t.id as id, " +
                "t.name as name, " +
                "t.price as price, " +
                "t.attachment.filePath as attachment, " +
                "t.category.nameUz as categoryName, " +
                "t.category.id as categoryId, " +
                "t.description as description " +
                "from Product t" + conQuery + orderQuery;


        Query productResult = entityManager.createQuery(query);
        if (categorySort != null) productResult.setParameter("categorySort", categorySort);
        productResult.setFirstResult(page * 10);
        productResult.setMaxResults(10);

//        List<Object[]> resultList = productResult.getResultList();
//
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Object[] array : resultList) {
//            for (Object obj : array) {
//                stringBuilder.append(obj).append(", ");
//            }
//            stringBuilder.setLength(stringBuilder.length() - 2); // Remove the last comma and space
//            stringBuilder.append("\n");
//        }
//        System.out.println(stringBuilder.toString());


        List<ProductResponse> response = ((List<Object[]>) productResult.getResultList()).stream().map(objects ->
                new ProductResponse(
                        UUID.fromString(objects[0].toString()),
                        objects[1].toString(),
                        BigDecimal.valueOf(Double.parseDouble(objects[2].toString())),
                        objects[3].toString(),
                        new CategoryResponse(UUID.fromString(objects[5].toString()), objects[4].toString()),
                        objects[6].toString()
                )).collect(Collectors.toList());




        Query totalAreaQuery = entityManager.createQuery("select count(t.id) from Product t ");
        long __sb = (long) totalAreaQuery.getSingleResult();

        Pageable pageable = PageRequest.of(page, 10);


        return new ApiResponse(true, new PageImpl<>(response, pageable, __sb), "Products");
    }
}
