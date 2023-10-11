package uz.fastfood.dashboard.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.*;
import uz.fastfood.dashboard.entity.enums.OrderStatus;
import uz.fastfood.dashboard.entity.enums.RoleName;
import uz.fastfood.dashboard.entity.enums.Status;
import uz.fastfood.dashboard.repository.*;
import uz.fastfood.dashboard.service.DistanceService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DistanceService distanceService;


    @Transactional
    @Override
    public void run(String... args) {

        distanceService.calculateDistance(41.3416675, 69.3364522, 41.3336437, 69.2693621);


        if (userRepository.count() == 0) {

            Role role_user = roleRepository.save(Role.builder().name(RoleName.ROLE_USER).build());
            Role role_admin = roleRepository.save(Role.builder().name(RoleName.ROLE_ADMIN).build());
            Role role_super_admin = roleRepository.save(Role.builder().name(RoleName.ROLE_SUPER_ADMIN).build());
            Role role_operator = roleRepository.save(Role.builder().name(RoleName.ROLE_OPERATOR).build());
            Role role_currier = roleRepository.save(Role.builder().name(RoleName.ROLE_CURRIER).build());
            userRepository.save(User.builder()
                    .username("admin")
                    .phoneNumber("+998991234567")
                    .password(passwordEncoder.encode("password"))
                    .firstname("Adminjon")
                    .lastname("Adminjonov")
                    .status(Status.ACTIVE)
                    .roles(Set.of(role_user, role_admin, role_super_admin, role_currier))
                    .build());


            User operator1 = userRepository.save(User.builder()
                    .firstname("Test")
                    .lastname("Operator")
                    .password(passwordEncoder.encode("root123"))
                    .username("operator1")
                    .phoneNumber("+998941234567")
                    .status(Status.ACTIVE)
                    .roles(Set.of(role_user, role_operator))
                    .build());
            User operator2 = userRepository.save(User.builder()
                    .firstname("Operator")
                    .lastname("tog'o")
                    .password(passwordEncoder.encode("root123"))
                    .username("operator2")
                    .phoneNumber("+998911234567")
                    .status(Status.ACTIVE)
                    .roles(Set.of(role_user, role_operator))
                    .build());


            User customer = userRepository.save(User.builder()
                    .firstname("Deveeprasad")
                    .lastname("Acharya")
                    .password(passwordEncoder.encode("root123"))
                    .username("customer")
                    .phoneNumber("+998971234567")
                    .status(Status.ACTIVE)
                    .roles(Set.of(role_user))
                    .build());


        }


        if (branchRepository.count() == 0) {
            List<Branch> branches = branchRepository.saveAll(
                    List.of(
                            Branch.builder()
                                    .landmark("Metro ro’parasida")
                                    .nameUz("Maksim Gorgiy")
                                    .nameRu("Maksim Gorgiy")
                                    .latitude(48.858844)
                                    .longitude(2.294351)
                                    .workingTime("09:00 - 20:00")
                                    .build(),
                            Branch.builder()
                                    .landmark("Royson dom oldida")
                                    .nameUz("Xadra fast food")
                                    .nameRu("Xadra fast food")
                                    .latitude(40.689247)
                                    .longitude(-74.044502)
                                    .workingTime("09:00 - 20:00")
                                    .build(),
                            Branch.builder()
                                    .landmark("Metro ro’parasida")
                                    .nameUz("Shaxrishton")
                                    .nameRu("Shaxrishton")
                                    .latitude(38.8951)
                                    .longitude(-77.0364)
                                    .workingTime("09:00 - 20:00")
                                    .build()
                    )
            );
        }

        if (categoryRepository.count() == 0) {
            List<Category> fastFoodCategory = categoryRepository.saveAll(List.of(
                    Category.builder()
                            .nameUz("Burger uz")
                            .nameRu("Burger ru")
                            .build(),
                    Category.builder()
                            .nameUz("Lavash uz")
                            .nameRu("Lavash ru")
                            .build()
            ));

            Category drinks = categoryRepository.save(Category.builder()
                    .nameUz("Ichimliklar uz")
                    .nameRu("Ichimliklar ru")
                    .build());


            List<Category> drinksCategory = categoryRepository.saveAll(List.of(
                    Category.builder()
                            .parentId(drinks.getId())
                            .nameUz("Issiq ichimliklar uz")
                            .nameRu("Issiq ichimliklar ru")
                            .build(),
                    Category.builder()
                            .parentId(drinks.getId())
                            .nameUz("Salqin ichimliklar uz")
                            .nameRu("Salqin ichimliklar ru")
                            .build()
            ));


            drinks.setChildCategories(new HashSet<>(drinksCategory));
            categoryRepository.save(drinks);

            List<Attachment> attachments = attachmentRepository.saveAll(List.of(
                    new Attachment("https://www.pasta.uz/upload/products/OL%20x%20Pasta%20Tandir%20lavash.jpg"),
                    new Attachment("https://bakeitwithlove.com/wp-content/uploads/2021/05/McDonalds-The-Travis-Scott-Burger-sq.jpg"),
                    new Attachment("https://images.uzum.uz/cf7p3f2vtie1lhbhc7ig/original.jpg")
            ));


            List<Product> products = productRepository.saveAll(List.of(
                    Product.builder()
                            .name("Lavash mini")
                            .price(BigDecimal.valueOf(18000))
                            .description("Kichkina lavash, bla bla")
                            .categoryId(fastFoodCategory.get(1).getId())
                            .attachmentId(attachments.get(0).getId())
                            .build(),
                    Product.builder()
                            .name("ChizburgerMax")
                            .price(BigDecimal.valueOf(23000))
                            .description("Burger, bla bla")
                            .categoryId(fastFoodCategory.get(0).getId())
                            .attachmentId(attachments.get(1).getId())
                            .build(),
                    Product.builder()
                            .name("Lavash max")
                            .price(BigDecimal.valueOf(26000))
                            .description("Kichkina lavash, bla bla")
                            .categoryId(fastFoodCategory.get(1).getId())
                            .attachmentId(attachments.get(0).getId())
                            .build(),
                    Product.builder()
                            .name("Burger mini")
                            .price(BigDecimal.valueOf(13000))
                            .description("Burger, bla bla")
                            .categoryId(fastFoodCategory.get(0).getId())
                            .attachmentId(attachments.get(1).getId())
                            .build(),
                    Product.builder()
                            .name("Coca-Cola 1,5")
                            .price(BigDecimal.valueOf(13000))
                            .description("Ichimlik, bla bla")
                            .categoryId(drinksCategory.get(0).getId())
                            .attachmentId(attachments.get(2).getId())
                            .build(),
                    Product.builder()
                            .name("Ko'k choy")
                            .price(BigDecimal.valueOf(3000))
                            .description("Choy, bla bla")
                            .categoryId(drinksCategory.get(1).getId())
                            .attachmentId(attachments.get(2).getId())
                            .build()
            ));

            if (orderRepository.count() == 0) {

                User customer = userRepository.findByUsername("customer").get();

                List<Branch> branches = branchRepository.findAllByDeletedFalse();


                List<OrderItem> orderItemList1 = orderItemRepository.saveAll(List.of(
                        OrderItem.builder()
                                .quantity(4)
                                .product(products.get(0))
                                .build(),
                        OrderItem.builder()
                                .quantity(4)
                                .product(products.get(1))
                                .build(),
                        OrderItem.builder()
                                .quantity(2)
                                .product(products.get(2))
                                .build()
                ));

                List<OrderItem> orderItemList2 = orderItemRepository.saveAll(List.of(
                        OrderItem.builder()
                                .quantity(3)
                                .product(products.get(4))
                                .build(),
                        OrderItem.builder()
                                .quantity(1)
                                .product(products.get(5))
                                .build(),
                        OrderItem.builder()
                                .quantity(6)
                                .product(products.get(3))
                                .build()
                ));


                Order order1 = orderRepository.save(Order.builder()
                        .operator(userRepository.findByUsername("operator1").get())
                        .branch(branches.get(0))
                        .customer(customer)
                        .orderCost(BigDecimal.valueOf(200000))
                        .orderNumber(1)
                        .shippingCost(BigDecimal.valueOf(1000))
                        .orderStatus(OrderStatus.PENDING)
                        .latitude(38.8976804)
                        .longitude(-77.0391101)
                        .orderItems(orderItemList1)
                        .build());

                Order order2 = orderRepository.save(Order.builder()
                        .operator(userRepository.findByUsername("operator2").get())
                        .branch(branches.get(0))
                        .customer(customer)
                        .orderCost(BigDecimal.valueOf(156000))
                        .shippingCost(BigDecimal.valueOf(6000))
                        .orderNumber(1)
                        .orderStatus(OrderStatus.PENDING)
                        .latitude(23.8776804)
                        .longitude(-55.0391101)
                        .orderItems(orderItemList1)
                        .build());

                orderItemList1.forEach(orderItem -> orderItem.setOrder(order1));
                orderItemRepository.saveAll(orderItemList1);
                orderItemList2.forEach(orderItem -> orderItem.setOrder(order2));
                orderItemRepository.saveAll(orderItemList2);

            }


        }


    }
}
