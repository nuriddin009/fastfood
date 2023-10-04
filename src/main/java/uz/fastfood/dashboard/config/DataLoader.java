package uz.fastfood.dashboard.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.dto.request.OrderItemRequest;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.*;
import uz.fastfood.dashboard.entity.enums.RoleName;
import uz.fastfood.dashboard.repository.*;
import uz.fastfood.dashboard.service.OrderService;

import java.math.BigDecimal;
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
    private final OrderService service;

    @Transactional
    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {

            Role role_user = roleRepository.save(Role.builder().name(RoleName.ROLE_USER).build());
            Role role_admin = roleRepository.save(Role.builder().name(RoleName.ROLE_ADMIN).build());
            Role role_super_admin = roleRepository.save(Role.builder().name(RoleName.ROLE_SUPER_ADMIN).build());
            userRepository.save(User.builder()
                    .username("admin")
                    .phoneNumber("+998991234567")
                    .password(passwordEncoder.encode("password"))
                    .firstname("Adminjon")
                    .lastname("Adminjonov")
                    .roles(Set.of(role_user, role_admin, role_super_admin))
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
                service.makeOrder(
                        new OrderRequest(
                                List.of(
                                        new OrderItemRequest(products.get(0).getId(), 2),
                                        new OrderItemRequest(products.get(1).getId(), 3),
                                        new OrderItemRequest(products.get(2).getId(), 5)
                                ),
                                38.8976804, -77.0391101
                        ),
                        new BaseResponse<>()
                );
                service.makeOrder(
                        new OrderRequest(
                                List.of(
                                        new OrderItemRequest(products.get(3).getId(), 1),
                                        new OrderItemRequest(products.get(4).getId(), 2),
                                        new OrderItemRequest(products.get(5).getId(), 4)
                                ),
                                48.8976804, -72.0391101
                        ),
                        new BaseResponse<>()
                );
            }


        }


    }
}
