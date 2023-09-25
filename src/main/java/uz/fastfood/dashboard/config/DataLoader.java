package uz.fastfood.dashboard.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.*;
import uz.fastfood.dashboard.entity.enums.RoleName;
import uz.fastfood.dashboard.repository.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static uz.fastfood.dashboard.entity.Branch.*;


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
                            builder()
                                    .landmark("Metro ro’parasida")
                                    .nameUz("Maksim Gorgiy")
                                    .nameRu("Maksim Gorgiy")
                                    .latitude(38.8951)
                                    .longitude(-77.0364)
                                    .workingTime("09:00 - 20:00")
                                    .build(),
                            builder()
                                    .landmark("Royson dom oldida")
                                    .nameUz("Xadra fast food")
                                    .nameRu("Xadra fast food")
                                    .latitude(38.8951)
                                    .longitude(-77.0364)
                                    .workingTime("09:00 - 20:00")
                                    .build(),
                            builder()
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
                            .parent(drinks)
                            .nameUz("Issiq ichimliklar uz")
                            .nameRu("Issiq ichimliklar ru")
                            .build(),
                    Category.builder()
                            .parent(drinks)
                            .nameUz("Salqin ichimliklar uz")
                            .nameRu("Salqin ichimliklar ru")
                            .build()
            ));

            List<Attachment> attachments = attachmentRepository.saveAll(List.of(
                    new Attachment("https://www.pasta.uz/upload/products/OL%20x%20Pasta%20Tandir%20lavash.jpg"),
                    new Attachment("https://bakeitwithlove.com/wp-content/uploads/2021/05/McDonalds-The-Travis-Scott-Burger-sq.jpg"),
                    new Attachment("https://images.uzum.uz/cf7p3f2vtie1lhbhc7ig/original.jpg")
            ));


            productRepository.saveAll(List.of(
                    Product.builder()
                            .name("Lavash mini")
                            .price(BigDecimal.valueOf(18000))
                            .description("Kichkina lavash, bla bla")
                            .category(fastFoodCategory.get(1))
                            .attachment(attachments.get(0))
                            .build(),
                    Product.builder()
                            .name("ChizburgerMax")
                            .price(BigDecimal.valueOf(23000))
                            .description("Burger, bla bla")
                            .category(fastFoodCategory.get(0))
                            .attachment(attachments.get(1))
                            .build(),
                    Product.builder()
                            .name("Lavash max")
                            .price(BigDecimal.valueOf(26000))
                            .description("Kichkina lavash, bla bla")
                            .category(fastFoodCategory.get(1))
                            .attachment(attachments.get(0))
                            .build(),
                    Product.builder()
                            .name("Burger mini")
                            .price(BigDecimal.valueOf(13000))
                            .description("Burger, bla bla")
                            .category(fastFoodCategory.get(0))
                            .attachment(attachments.get(1))
                            .build(),
                    Product.builder()
                            .name("Coca-Cola 1,5")
                            .price(BigDecimal.valueOf(13000))
                            .description("Ichimlik, bla bla")
                            .category(drinksCategory.get(0))
                            .attachment(attachments.get(2))
                            .build(),
                    Product.builder()
                            .name("Ko'k choy")
                            .price(BigDecimal.valueOf(3000))
                            .description("Choy, bla bla")
                            .category(drinksCategory.get(1))
                            .attachment(attachments.get(2))
                            .build()
            ));


        }


    }
}
